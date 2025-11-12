package com.mechanical_workshop_usm.work_order_module;

import com.mechanical_workshop_usm.picture_module.car_picture.CarPictureRepository;
import com.mechanical_workshop_usm.picture_module.dashboard_light.DashboardLightRepository;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.WorkOrderHasDashboardLight;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.WorkOrderHasDashboardLightRepository;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderFullRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderResponse;

import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedService;
import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedServiceRepository;
import com.mechanical_workshop_usm.work_service_module.WorkService;
import com.mechanical_workshop_usm.work_service_module.WorkServiceRepository;
import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import com.mechanical_workshop_usm.record_module.record.Record;
import com.mechanical_workshop_usm.record_module.record.RecordRepository;
import com.mechanical_workshop_usm.picture_module.picture.PictureRepository;
import com.mechanical_workshop_usm.picture_module.car_picture.CarPicture;

import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.dto.CreateWorkOrderHasDashboardLightRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final RecordRepository recordRepository;
    private final PictureRepository pictureRepository;
    private final CarPictureRepository carPictureRepository;
    private final WorkServiceRepository workServiceRepository;
    private final WorkOrderRealizedServiceRepository realizedServiceRepository;
    private final DashboardLightRepository dashboardLightRepository;
    private final WorkOrderHasDashboardLightRepository workOrderHasDashboardLightRepository;

    private final Path baseDir;

    public WorkOrderService(
            WorkOrderRepository workOrderRepository,
            RecordRepository recordRepository,
            PictureRepository pictureRepository,
            CarPictureRepository carPictureRepository,
            WorkServiceRepository workServiceRepository,
            WorkOrderRealizedServiceRepository realizedServiceRepository,
            DashboardLightRepository dashboardLightRepository,
            WorkOrderHasDashboardLightRepository workOrderHasDashboardLightRepository
    ) {
        this.workOrderRepository = workOrderRepository;
        this.recordRepository = recordRepository;
        this.pictureRepository = pictureRepository;
        this.carPictureRepository = carPictureRepository;
        this.workServiceRepository = workServiceRepository;
        this.realizedServiceRepository = realizedServiceRepository;
        this.dashboardLightRepository = dashboardLightRepository;
        this.workOrderHasDashboardLightRepository = workOrderHasDashboard_lightRepositoryOrFallback(workOrderHasDashboardLightRepository);

        this.baseDir = Paths.get("./images/work-orders").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.baseDir);
        } catch (IOException ignored) {}
    }

    private WorkOrderHasDashboardLightRepository workOrderHasDashboard_lightRepositoryOrFallback(WorkOrderHasDashboardLightRepository repo) {
        return repo;
    }

    @Transactional
    public CreateWorkOrderResponse createFull(CreateWorkOrderFullRequest request, List<MultipartFile> carPictures, MultipartFile signature) {
        Record record = recordRepository.findById(request.recordId())
                .orElseThrow(() -> new IllegalArgumentException("Record not found: " + request.recordId()));

        WorkOrder workOrder = new WorkOrder(record, LocalDate.now(), LocalTime.now(), null);
        WorkOrder saved = workOrderRepository.save(workOrder);

        if (signature != null && !signature.isEmpty()) {
            try {
                String sigFilename = storeFile(signature, "signature-" + saved.getId());
                String sigPath = "/images/work-orders/" + sigFilename;
                saved.setSignaturePath(sigPath);
                workOrderRepository.save(saved);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to store signature file", e);
            }
        }

        if (carPictures != null && !carPictures.isEmpty()) {
            for (MultipartFile file : carPictures) {
                if (file == null || file.isEmpty()) continue;

                try {
                    String filename = storeFile(file, "carpic-" + saved.getId());
                    String publicPath = "/images/work-orders/" + filename;
                    CarPicture carPic = new CarPicture(saved, publicPath);
                    carPictureRepository.save(carPic);
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to store car picture file", e);
                }
            }
        }

        List<Integer> allServiceIds = new ArrayList<>();
        if (request.newServices() != null) {
            for (CreateWorkServiceRequest sReq : request.newServices()) {
                WorkService ws = new WorkService(sReq.serviceName(), LocalTime.parse(sReq.estimatedTime()));
                WorkService savedWs = workServiceRepository.save(ws);
                allServiceIds.add(savedWs.getId());
                WorkOrderRealizedService ros = new WorkOrderRealizedService(saved, savedWs, false);
                realizedServiceRepository.save(ros);
            }
        }

        if (request.serviceIds() != null) {
            for (Integer sid : request.serviceIds()) {
                workServiceRepository.findById(sid).ifPresent(ws -> {
                    WorkOrderRealizedService ros = new WorkOrderRealizedService(saved, ws, false);
                    realizedServiceRepository.save(ros);
                    allServiceIds.add(sid);
                });
            }
        }

        if (request.dashboardLightsActived() != null) {
            for (CreateWorkOrderHasDashboardLightRequest dlReq : request.dashboardLightsActived()) {
                dashboardLightRepository.findById(dlReq.dashboardLightId()).ifPresent(dl -> {
                    WorkOrderHasDashboardLight assoc = new WorkOrderHasDashboardLight(
                            saved,
                            dl,
                            dlReq.present() != null ? dlReq.present() : false,
                            dlReq.operates() != null ? dlReq.operates() : false
                    );
                    workOrderHasDashboardLightRepository.save(assoc);
                });
            }
        }

        return new CreateWorkOrderResponse(
                saved.getId(),
                record.getId(),
                saved.getEstimatedDate().toString(),
                saved.getEstimatedTime().toString(),
                saved.getSignaturePath()
        );
    }
    private String storeFile(MultipartFile file, String prefix) throws IOException {
        String original = Optional.ofNullable(file.getOriginalFilename()).orElse("file");
        String sanitized = original.replaceAll("[^a-zA-Z0-9._-]", "_");
        String filename = prefix + "-" + System.currentTimeMillis() + "-" + sanitized;
        Path target = baseDir.resolve(filename);
        try (var in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
        return filename;
    }
}