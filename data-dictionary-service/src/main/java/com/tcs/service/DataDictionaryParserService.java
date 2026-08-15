package com.tcs.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.tcs.dto.DataDictionarySchema;
import com.tcs.dto.FieldSchema;
import com.tcs.dto.TableSchema;

@Service
public class DataDictionaryParserService {

	private final ObjectMapper objectMapper=new ObjectMapper();
	
	public DataDictionarySchema parse(MultipartFile file) throws IOException, CsvException {
		System.out.println("DataDictionaryParserService.parse()");
		String fileName=Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
		System.out.println("File name :: "+fileName);
		
		if(fileName.endsWith(".csv")) {
			return parseCsv(file);
		} else if(fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
			return parseExcel(file);
		} else if (fileName.endsWith(".json")) {
			return parseJson(file);
		} else {
			throw new IllegalArgumentException("Invalid file");
		}
	}
	
	private DataDictionarySchema parseCsv(MultipartFile file) throws IOException, CsvException{
		
		DataDictionarySchema schema=new DataDictionarySchema();
		try(CSVReader reader=new CSVReader(new InputStreamReader(file.getInputStream()))) {
			List<String[]> rows=reader.readAll();
			if(rows.isEmpty()) return schema;
			
			for(int i=1;i<rows.size();i++) {
				String[] row=rows.get(i);
				if(row.length< 3) continue;
				String tableName=row[0].trim();
				String fieldName=row[1].trim();
				String dataTypes=row[2].trim();
				List<String> constraints = row.length>3 && !row[3].isBlank()?
						List.of(row[2].split("\\s*,\\s*")):
							new ArrayList<String>();
				
				addField(schema,tableName,fieldName,dataTypes,constraints);
			}
		} catch(Exception e) {
			throw new IOException("Failed to parse CSV data dictionary: " + e.getMessage(), e);
		}
		 printParsedSchema(schema);
		return schema;
	}
	


	private DataDictionarySchema parseExcel(MultipartFile file) throws IOException {
		DataDictionarySchema schema=new DataDictionarySchema();
		
		try(InputStream is=file.getInputStream(); 
				Workbook workbook=WorkbookFactory.create(is)) {
			Sheet sheet=workbook.getSheetAt(0);
			for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header
 
                String tableName = getCellString(row, 0);
                String fieldName = getCellString(row, 1);
                String dataType = getCellString(row, 2);
                String constraintsRaw = getCellString(row, 3);
 
                if (tableName.isBlank() || fieldName.isBlank()) continue;
 
                List<String> constraints = constraintsRaw.isBlank()
                        ? new ArrayList<>()
                        : List.of(constraintsRaw.split("\\s*,\\s*"));
 
                addField(schema, tableName, fieldName, dataType, constraints);
            }
			
		} catch(Exception e) {
			throw new IOException("Failed to parse Excel data dictionary: " + e.getMessage(), e);
	}
		printParsedSchema(schema);
		return schema;
		
	}
	
	private DataDictionarySchema parseJson(MultipartFile file) throws IOException{
		try(InputStream is=file.getInputStream()) {
			JsonNode root=objectMapper.readTree(is);
			if(root.has("tables")) {
				return objectMapper.treeToValue(root, DataDictionarySchema.class);
			}
			throw new IllegalArgumentException("JSON data dictionary must contain a top-level 'tables' array");
		} catch(Exception e) {
			 throw new IOException("Failed to parse JSON data dictionary: " + e.getMessage(), e);
		}
	}
	
	private void addField(DataDictionarySchema schema, String tableName, String fieldName, String dataTypes,
			List<String> constraints) {
		TableSchema table=schema.getTables().stream()
				.filter(t->t.getTableName().equalsIgnoreCase(tableName))
				.findFirst()
				.orElseGet(()->{
					TableSchema t=new TableSchema(tableName,new ArrayList<>());
					schema.getTables().add(t);
					return t;
				});
		table.getFields().add(new FieldSchema(fieldName,dataTypes,constraints));
	}
	
	private String getCellString(Row row, int cellIndex) {
	    if (row.getCell(cellIndex) == null) {
	        return "";
	    }

	    return row.getCell(cellIndex).toString();
	}
	
    private void printParsedSchema(DataDictionarySchema schema) {
        System.out.println("========== PARSED DATA DICTIONARY ==========");
        for (TableSchema table : schema.getTables()) {
            System.out.println("Table: " + table.getTableName());
            for (FieldSchema field : table.getFields()) {
                System.out.println("   - " + field.getName()
                        + " (" + field.getType() + ")"
                        + (field.getConstraints().isEmpty() ? "" : " " + field.getConstraints()));
            }
        }
        System.out.println("==============================================");
    }
}
