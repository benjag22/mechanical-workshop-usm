package com.mechanical_workshop_usm.picture_module.dashboard_light;

import com.mechanical_workshop_usm.picture_module.picture.dto.CreatePictureResponse;
import com.mechanical_workshop_usm.util.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DashboardLightService {

    private final DashboardLightRepository repository;
    private final Path baseDir;
    private final String baseUrl;

    public DashboardLightService(
            DashboardLightRepository repository,
            @Value("${storage.base-dir:./images}") String storageBaseDir,
            @Value("${storage.base-url:http://localhost:8081}") String storageBaseUrl) {
        this.repository = repository;
        this.baseDir = Paths.get(storageBaseDir).toAbsolutePath().normalize();
        this.baseUrl = storageBaseUrl.endsWith("/") ? storageBaseUrl.substring(0, storageBaseUrl.length() - 1) : storageBaseUrl;
    }

    @Transactional
    public CreatePictureResponse createFromMultipart(MultipartFile image, String alt) {
        try {
            if (image == null || image.isEmpty()) {
                throw new IllegalArgumentException("Image file is required");
            }

            Path targetDir = baseDir.resolve("dashboard");
            Files.createDirectories(targetDir);

            String original = Optional.ofNullable(image.getOriginalFilename()).orElse("unnamed");
            String basename = FilenameUtils.getBaseName(original);
            String ext = FilenameUtils.getExtension(original);

            String sanitizedBase = FilenameUtils.sanitizeForFilename(basename);
            String unique = UUID.randomUUID().toString();
            String filename = sanitizedBase.isEmpty()
                    ? unique + (ext.isBlank() ? "" : "." + ext)
                    : unique + "-" + sanitizedBase + (ext.isBlank() ? "" : "." + ext);

            Path target = targetDir.resolve(filename);

            try (var in = image.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            String publicPath = "/images/dashboard/" + filename;
            String publicUrl = baseUrl + publicPath;

            String altFinal;
            if (alt != null && !alt.isBlank()) altFinal = alt.trim();
            else altFinal = sanitizedBase.isEmpty() ? "" : sanitizedBase;

            DashboardLight entity = new DashboardLight(altFinal, publicUrl);
            DashboardLight saved = repository.save(entity);

            return new CreatePictureResponse(
                    saved.getId(),
                    saved.getAlt(),
                    saved.getPath()
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to store file", e);
        }
    }

    @Transactional(readOnly = true)
    public CreatePictureResponse getById(Integer id) {
        DashboardLight d = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("DashboardLight not found: " + id));
        return new CreatePictureResponse(d.getId(), d.getAlt(), d.getPath());
    }

    @Transactional(readOnly = true)
    public List<CreatePictureResponse> getAll() {
        return repository.findAll().stream()
                .map(d -> new CreatePictureResponse(d.getId(), d.getAlt(), d.getPath()))
                .toList();
    }
}