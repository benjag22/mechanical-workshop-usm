package com.mechanical_workshop_usm.picture_module.commons;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public final class StorageUtils {

    private StorageUtils() {}

    public static Path toBaseDir(String storageBaseDir) {
        return Paths.get(storageBaseDir).toAbsolutePath().normalize();
    }

    public static String normalizeBaseUrl(String storageBaseUrl) {
        return storageBaseUrl.endsWith("/") ?
                storageBaseUrl.substring(0, storageBaseUrl.length() - 1) :
                storageBaseUrl;
    }

    public static String extractExtensionWithDot(String original) {
        if (original == null) return "";
        int idx = original.lastIndexOf('.');
        return (idx == -1) ? "" : original.substring(idx);
    }

    public static String saveMultipartFile(Path baseDir, String subdir, MultipartFile image) throws IOException {
        Path targetDir = baseDir.resolve(subdir);
        Files.createDirectories(targetDir);

        String ext = extractExtensionWithDot(image.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + ext;
        Path target = targetDir.resolve(filename);

        Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    public static String publicPath(String subdir, String filename) {
        return "/images/" + subdir + "/" + filename;
    }
}