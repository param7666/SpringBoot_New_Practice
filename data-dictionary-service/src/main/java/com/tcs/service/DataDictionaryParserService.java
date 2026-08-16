package com.tcs.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
	

	
	public String parse(MultipartFile file) throws IOException {
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
        String rawText;
 
        if (fileName.endsWith(".csv")) {
            rawText = parseCsv(file);
        } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            rawText = parseAllSheets(file);
        } else if (fileName.endsWith(".json")) {
            rawText = parseJson(file);
        } else {
            throw new IllegalArgumentException("Unsupported data dictionary format: " + fileName
                    + ". Supported: .csv, .xlsx, .json");
        }
 
        printParsedText(rawText); // DEBUG: remove/disable once confirmed working
        return rawText;
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
    
    
    private String parseAllSheets(MultipartFile file) throws IOException {
        StringBuilder sb = new StringBuilder();
        DataFormatter formatter = new DataFormatter(); // safely stringifies any cell type
 
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            int sheetCount = workbook.getNumberOfSheets();
 
            for (int s = 0; s < sheetCount; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                sb.append("--- SHEET: ").append(sheet.getSheetName()).append(" ---\n");
 
                for (Row row : sheet) {
                    StringBuilder rowText = new StringBuilder();
                    boolean rowHasContent = false;
 
                    for (Cell cell : row) {
                        String value = formatter.formatCellValue(cell).trim();
                        if (!value.isEmpty()) rowHasContent = true;
                        rowText.append(value).append(" | ");
                    }
 
                    if (rowHasContent) {
                        sb.append(rowText).append("\n");
                    }
                }
                sb.append("\n");
            }
        } catch (Exception e) {
            throw new IOException("Failed to parse Excel data dictionary: " + e.getMessage(), e);
        }
        return sb.toString();
    }
    
    
    private String parseCsv(MultipartFile file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                sb.append(String.join(" | ", row)).append("\n");
            }
        } catch (Exception e) {
            throw new IOException("Failed to parse CSV data dictionary: " + e.getMessage(), e);
        }
        return sb.toString();
    }
    
    private String parseJson(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            return new String(is.readAllBytes());
        } catch (Exception e) {
            throw new IOException("Failed to parse JSON data dictionary: " + e.getMessage(), e);
        }
    }
 
    // ---------------------------------------------------------------
    private void printParsedText(String text) {
        System.out.println("========== PARSED DATA DICTIONARY (ALL SHEETS) ==========");
        System.out.println(text);
        System.out.println("===========================================================");
    }
}
