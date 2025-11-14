package com.mechanical_workshop_usm.picture_module.dashboard_light;

import com.mechanical_workshop_usm.picture_module.picture.dto.GetPictureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class DashboardLightService {

    private final DashboardLightRepository repository;
    private final Path baseDir;
    private final String baseUrl;

    public DashboardLightService(
            DashboardLightRepository repository,
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
            Path targetDir = baseDir.resolve("dashboard");
            Files.createDirectories(targetDir);

            String original = image.getOriginalFilename();
            String ext = "";
            if (original != null) {
                int idx = original.lastIndexOf('.');
                if (idx != -1) ext = original.substring(idx);
            }

            String filename = UUID.randomUUID() + ext;
            Path target = targetDir.resolve(filename);
            Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            String publicPath = "/images/dashboard/" + filename;

            DashboardLight entity = new DashboardLight(alt == null ? "" : alt, publicPath);
            DashboardLight saved = repository.save(entity);

            return new GetPictureResponse(saved.getId(), saved.getAlt(), baseUrl + saved.getPath());

        } catch (IOException e) {
            throw new IllegalStateException("Error storing file", e);
        }
    }

    public GetPictureResponse getById(Integer id) {
        DashboardLight d = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        return new GetPictureResponse(d.getId(), d.getAlt(), baseUrl + d.getPath());
    }

    public List<GetPictureResponse> getAll() {
        return repository.findAll().stream()
                .map(d -> new GetPictureResponse(d.getId(), d.getAlt(), baseUrl + d.getPath()))
                .toList();
    }
}