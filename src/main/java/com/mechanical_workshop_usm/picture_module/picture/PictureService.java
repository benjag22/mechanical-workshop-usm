package com.mechanical_workshop_usm.picture_module.picture;

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
public class PictureService {

    private final PictureRepository repository;
    private final Path baseDir;
    private final String baseUrl;

    public PictureService(
            PictureRepository repository,
            @Value("${storage.base-dir:./images}") String storageBaseDir,
            @Value("${storage.base-url:http://localhost:8081}") String storageBaseUrl
    ) {
        this.repository = repository;
        this.baseDir = Paths.get(storageBaseDir).toAbsolutePath().normalize();
        this.baseUrl = storageBaseUrl.endsWith("/")
                ? storageBaseUrl.substring(0, storageBaseUrl.length() - 1)
                : storageBaseUrl;
    }

    @Transactional
    public CreatePictureResponse createFromMultipart(MultipartFile image, String alt) {
        try {
            if (image == null || image.isEmpty()) {
                throw new IllegalArgumentException("Image file is required");
            }

            Path targetDir = baseDir.resolve("pictures");
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

            String publicPath = "/images/pictures/" + filename;
            String publicUrl = baseUrl + publicPath;

            String altFinal = (alt != null && !alt.isBlank())
                    ? alt.trim()
                    : (sanitizedBase.isEmpty() ? "" : sanitizedBase);

            Picture entity = new Picture(altFinal, publicUrl);
            Picture saved = repository.save(entity);

            return new CreatePictureResponse(
                    saved.getId(),
                    saved.getAlt(),
                    saved.getPath()
            );

        } catch (IOException e) {
            throw new IllegalStateException("Failed to store picture file", e);
        }
    }

    @Transactional(readOnly = true)
    public CreatePictureResponse getById(Integer id) {
        Picture p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Picture not found: " + id));
        return new CreatePictureResponse(p.getId(), p.getAlt(), p.getPath());
    }

    @Transactional(readOnly = true)
    public List<CreatePictureResponse> getAll() {
        return repository.findAll().stream()
                .map(p -> new CreatePictureResponse(p.getId(), p.getAlt(), p.getPath()))
                .toList();
    }
}
