package com.tcs.service;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;

@Service
public class DataDictionaryParserService {

    private static final Logger log = LoggerFactory.getLogger(DataDictionaryParserService.class);

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

        log.debug("Parsed data dictionary ({} chars): {}", rawText.length(), fileName);
        return rawText;
    }

    private String parseAllSheets(MultipartFile file) throws IOException {
        StringBuilder sb = new StringBuilder();
        DataFormatter formatter = new DataFormatter();

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
}
