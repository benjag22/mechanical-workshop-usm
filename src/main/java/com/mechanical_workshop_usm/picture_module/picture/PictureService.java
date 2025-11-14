package com.mechanical_workshop_usm.picture_module.picture;

import com.mechanical_workshop_usm.picture_module.picture.dto.GetPictureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class PictureService {

    private final PictureRepository repository;
    private final Path baseDir;
    private final String baseUrl;

    public PictureService(
            PictureRepository repository,
            @Value("${storage.base-dir}") String storageBaseDir,
            @Value("${storage.base-url}") String storageBaseUrl
    ) {
        this.repository = repository;
        this.baseDir = Paths.get(storageBaseDir).toAbsolutePath().normalize();
        this.baseUrl = storageBaseUrl.endsWith("/") ?
                storageBaseUrl.substring(0, storageBaseUrl.length() - 1) :
                storageBaseUrl;
    }

    public GetPictureResponse createFromMultipart(MultipartFile image, String alt) {
        try {
            Path targetDir = baseDir.resolve("pictures");
            Files.createDirectories(targetDir);

            String original = image.getOriginalFilename();
            String ext = "";
            if (original != null) {
                int idx = original.lastIndexOf('.');
                if (idx != -1) ext = original.substring(idx);
            }

            String filename = UUID.randomUUID().toString() + ext;
            Path target = targetDir.resolve(filename);
            Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            String publicPath = "/images/pictures/" + filename;

            Picture entity = new Picture(alt == null ? "" : alt, publicPath);
            Picture saved = repository.save(entity);

            return new GetPictureResponse(saved.getId(), saved.getAlt(), baseUrl + saved.getPath());
        } catch (IOException e) {
            throw new IllegalStateException("Error storing picture file", e);
        }
    }

    public GetPictureResponse getById(Integer id) {
        Picture p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        return new GetPictureResponse(p.getId(), p.getAlt(), baseUrl + p.getPath());
    }

    public List<GetPictureResponse> getAll() {
        return repository.findAll().stream()
                .map(p -> new GetPictureResponse(p.getId(), p.getAlt(), baseUrl + p.getPath()))
                .toList();
    }
}