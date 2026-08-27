package com.tcs;

import com.tcs.service.DataDictionaryParserService;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Standalone runner to sanity-check DataDictionaryParserService.parse()
 * against a real file on disk, without needing to start the full Spring
 * app or make an actual HTTP request.
 *
 * Just a plain main() — not a Spring bean, not wired into the app context.
 */

public class ParserTestRunner  {

    public static void main(String[] args) throws Exception {

        // 👉 Update this path to point at your actual .xlsx file
        Path filePath = Paths.get("C:\\Users\\parme\\Downloads\\AI_Project_Generator_Data_Dictionary.xlsx");

        try (FileInputStream fis = new FileInputStream(filePath.toFile())) {

            MultipartFile multipartFile = new MockMultipartFile(
                    "dataDictionaryFile",           // form field name (not important here)
                    filePath.getFileName().toString(), // original filename — parse() uses this to detect extension
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    fis
            );

            DataDictionaryParserService parserService = new DataDictionaryParserService();

            String result = parserService.parse(multipartFile);

            System.out.println("========== PARSED RESULT ==========");
            System.out.println(result);
            System.out.println("========== LENGTH: " + result.length() + " chars ==========");
        }
    }
}
