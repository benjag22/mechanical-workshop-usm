package com.mechanical_workshop_usm.picture_module.dashboard_light;

import com.mechanical_workshop_usm.picture_module.picture.dto.GetPictureResponse;
import com.mechanical_workshop_usm.picture_module.commons.StorageUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

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
        this.baseDir = StorageUtils.toBaseDir(storageBaseDir);
        this.baseUrl = StorageUtils.normalizeBaseUrl(storageBaseUrl);
    }

    public GetPictureResponse createFromMultipart(MultipartFile image, String alt) {
        try {
            String filename = StorageUtils.saveMultipartFile(baseDir, "dashboard", image);
            String publicPath = StorageUtils.publicPath("dashboard", filename);

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