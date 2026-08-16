package com.tcs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Saves the wireframe file to disk immediately when it's received, so the
 * background job (running on a different thread, after the original HTTP
 * request has already ended) can read it later without depending on the
 * original MultipartFile, which is not guaranteed to still be valid then.
 */
@Service

public class WireframeStorageService {

    private Path storageDir;

    
    public WireframeStorageService(@Value("${storage.wireframe-dir}") String storageDirPath) throws IOException {
        this.storageDir = Paths.get(storageDirPath);
        Files.createDirectories(this.storageDir);
    }

    /**
     * Saves the file and returns the absolute path it was stored at.
     */
    public String store(MultipartFile file, UUID generationId) throws IOException {
        String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "wireframe";
        String safeName = generationId + "_" + originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path target = storageDir.resolve(safeName);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toAbsolutePath().toString();
    }
}
