package com.tcs.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.tcs.dto.EntitySpec;
import com.tcs.dto.FieldSpec;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Reads .template files from src/main/resources/templates and fills
 * placeholders using entity spec data. Templates use {{PLACEHOLDER}} syntax.
 */
@Service
public class JavaCodeTemplateService {

    public String loadTemplate(String templateName) throws IOException {
        try (InputStream is = new ClassPathResource("templates/" + templateName).getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public String buildEntityFile(String template, String packageName, EntitySpec entity) {
        String fieldsBlock = buildFieldsBlock(entity.getFields());

        return template
                .replace("{{PACKAGE_NAME}}", packageName)
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{TABLE_NAME}}", entity.getTableName())
                .replace("{{FIELDS}}", fieldsBlock);
    }

    public String buildRepositoryFile(String template, String packageName, EntitySpec entity) {
        return template
                .replace("{{PACKAGE_NAME}}", packageName)
                .replace("{{ENTITY_NAME}}", entity.getName());
    }

    public String buildServiceFile(String template, String packageName, EntitySpec entity) {
        return template
                .replace("{{PACKAGE_NAME}}", packageName)
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{ENTITY_NAME_LOWER}}", lowerFirst(entity.getName()));
    }

    public String buildControllerFile(String template, String packageName, EntitySpec entity) {
        return template
                .replace("{{PACKAGE_NAME}}", packageName)
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{ENTITY_NAME_LOWER}}", lowerFirst(entity.getName()))
                .replace("{{TABLE_NAME}}", entity.getTableName());
    }

    public String buildDtoFile(String template, String packageName, EntitySpec entity) {
        String fieldsBlock = buildFieldsBlock(entity.getFields());
        return template
                .replace("{{PACKAGE_NAME}}", packageName)
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{FIELDS}}", fieldsBlock);
    }

    private String buildFieldsBlock(List<FieldSpec> fields) {
        StringBuilder sb = new StringBuilder();
        for (FieldSpec field : fields) {
            if (field.isPrimaryKey()) {
                sb.append("    @Id\n")
                  .append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n")
                  .append("    private ").append(field.getType()).append(" ").append(field.getName()).append(";\n\n");
            } else {
                sb.append("    @Column(nullable = ").append(!field.isRequired()).append(")\n")
                  .append("    private ").append(field.getType()).append(" ").append(field.getName()).append(";\n\n");
            }
        }
        return sb.toString();
    }

    private String lowerFirst(String s) {
        return s.isEmpty() ? s : Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}