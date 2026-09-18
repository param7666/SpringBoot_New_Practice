package com.tcs.util;


import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class ZipUtils {

    /**
     * Extracts zip bytes into a fresh temp directory. Returns that directory's path.
     */
    public static Path unzipToTempDir(byte[] zipBytes, String prefix) throws IOException {
        Path tempDir = Files.createTempDirectory(prefix);

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path target = tempDir.resolve(entry.getName()).normalize();

                if (!target.startsWith(tempDir)) {
                    throw new IOException("Zip entry escapes target directory: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(target);
                } else {
                    Files.createDirectories(target.getParent());
                    try (OutputStream os = Files.newOutputStream(target)) {
                        zis.transferTo(os);
                    }
                }
                zis.closeEntry();
            }
        }
        return tempDir;
    }

    /**
     * Zips an entire directory tree into raw bytes.
     */
    public static byte[] zipDirectory(Path sourceDir) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            String entryName = sourceDir.relativize(file).toString().replace("\\", "/");
                            zos.putNextEntry(new ZipEntry(entryName));
                            Files.copy(file, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to zip file: " + file, e);
                        }
                    });
        }
        return baos.toByteArray();
    }

    public static void deleteRecursively(Path dir) throws IOException {
        if (!Files.exists(dir)) return;
        Files.walk(dir)
                .sorted((a, b) -> b.compareTo(a)) // delete children before parents
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException ignored) {
                    }
                });
    }
}
