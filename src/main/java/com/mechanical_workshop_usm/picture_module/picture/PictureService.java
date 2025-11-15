package com.mechanical_workshop_usm.picture_module.picture;

import com.mechanical_workshop_usm.picture_module.commons.StorageUtils;
import com.mechanical_workshop_usm.picture_module.picture.dto.GetPictureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

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
        this.baseDir = StorageUtils.toBaseDir(storageBaseDir);
        this.baseUrl = StorageUtils.normalizeBaseUrl(storageBaseUrl);
    }

    public GetPictureResponse createFromMultipart(MultipartFile image, String alt) {
        try {
            String filename = StorageUtils.saveMultipartFile(baseDir, "pictures", image);
            String publicPath = StorageUtils.publicPath("pictures", filename);

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