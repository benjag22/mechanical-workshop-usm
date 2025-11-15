package com.mechanical_workshop_usm.picture_module.car_picture;

import com.mechanical_workshop_usm.picture_module.car_picture.dto.CreateCarPictureRequest;
import com.mechanical_workshop_usm.picture_module.car_picture.dto.GetCarPictureResponse;
import com.mechanical_workshop_usm.picture_module.commons.StorageUtils;
import com.mechanical_workshop_usm.work_order_module.WorkOrder;
import com.mechanical_workshop_usm.work_order_module.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarPictureService {

    private final CarPictureRepository repository;
    private final WorkOrderRepository workOrderRepository;
    private final Path baseDir;
    private final String baseUrl;

    public CarPictureService(
            CarPictureRepository repository,
            WorkOrderRepository workOrderRepository,
            @Value("${storage.base-dir}") String storageBaseDir,
            @Value("${storage.base-url}") String storageBaseUrl
    ) {
        this.repository = repository;
        this.workOrderRepository = workOrderRepository;
        this.baseDir = StorageUtils.toBaseDir(storageBaseDir);
        this.baseUrl = StorageUtils.normalizeBaseUrl(storageBaseUrl);
    }


    public void createFromRequest(CreateCarPictureRequest req) {
        try {
            Integer workOrderId = req.workOrderId();
            WorkOrder wo = workOrderRepository.findById(workOrderId)
                    .orElseThrow(() -> new IllegalArgumentException("WorkOrder not found: " + workOrderId));

            MultipartFile image = req.image();
            String filename = StorageUtils.saveMultipartFile(baseDir, "work-orders", image);
            String publicPath = StorageUtils.publicPath("work-orders", filename);

            CarPicture entity = new CarPicture(wo, publicPath);
            CarPicture saved = repository.save(entity);

            new GetCarPictureResponse(saved.getId(), saved.getWorkOrder().getId(), baseUrl + saved.getPath());
        } catch (IOException e) {
            throw new IllegalStateException("Error storing car picture file", e);
        }
    }


    public GetCarPictureResponse getById(Integer id) {
        CarPicture c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CarPicture not found: " + id));
        return new GetCarPictureResponse(c.getId(), c.getWorkOrder().getId(), baseUrl + c.getPath());
    }

    public List<GetCarPictureResponse> getByWorkOrderId(Integer workOrderId) {
        return repository.findByWorkOrder_Id(workOrderId).stream()
                .map(c -> new GetCarPictureResponse(c.getId(), c.getWorkOrder().getId(), baseUrl + c.getPath()))
                .collect(Collectors.toList());
    }

    public List<GetCarPictureResponse> getAll() {
        return repository.findAll().stream()
                .map(c -> new GetCarPictureResponse(c.getId(), c.getWorkOrder().getId(), baseUrl + c.getPath()))
                .collect(Collectors.toList());
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}