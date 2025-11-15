package com.mechanical_workshop_usm.work_order_module;

import com.mechanical_workshop_usm.picture_module.car_picture.CarPictureService;
import com.mechanical_workshop_usm.picture_module.car_picture.dto.CreateCarPictureRequest;
import com.mechanical_workshop_usm.picture_module.commons.StorageUtils;
import com.mechanical_workshop_usm.util.EntityFinder;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.WorkOrderHasDashboardLight;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.WorkOrderHasDashboardLightRepository;
import com.mechanical_workshop_usm.work_order_has_mechanic_module.WorkOrderHasMechanicService;
import com.mechanical_workshop_usm.work_order_has_mechanic_module.dto.CreateWorkOrderHasMechanicRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderResponse;
import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedService;
import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedServiceRepository;
import com.mechanical_workshop_usm.work_service_module.WorkService;
import com.mechanical_workshop_usm.work_service_module.WorkServiceRepository;
import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import com.mechanical_workshop_usm.record_module.record.Record;
import com.mechanical_workshop_usm.record_module.record.RecordRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final RecordRepository recordRepository;
    private final WorkServiceRepository workServiceRepository;
    private final WorkOrderRealizedServiceRepository realizedServiceRepository;
    private final WorkOrderHasDashboardLightRepository workOrderHasDashboardLightRepository;
    private final com.mechanical_workshop_usm.picture_module.dashboard_light.DashboardLightRepository dashboardLightRepository;

    private final CarPictureService carPictureService;
    private final WorkOrderHasMechanicService workOrderHasMechanicService;
    private final EntityFinder entityFinder;
    private final WorkOrderValidator workOrderValidator;

    private final Path baseDir;

    public WorkOrderService(
            WorkOrderRepository workOrderRepository,
            RecordRepository recordRepository,
            WorkServiceRepository workServiceRepository,
            WorkOrderRealizedServiceRepository realizedServiceRepository,
            com.mechanical_workshop_usm.picture_module.dashboard_light.DashboardLightRepository dashboardLightRepository,
            WorkOrderHasDashboardLightRepository workOrderHasDashboardLightRepository,
            CarPictureService carPictureService,
            WorkOrderHasMechanicService workOrderHasMechanicService,
            EntityFinder entityFinder,
            WorkOrderValidator workOrderValidator,
            @Value("${storage.base-dir}") String storageBaseDir
    ) {
        this.workOrderRepository = workOrderRepository;
        this.recordRepository = recordRepository;
        this.workServiceRepository = workServiceRepository;
        this.realizedServiceRepository = realizedServiceRepository;
        this.dashboardLightRepository = dashboardLightRepository;
        this.workOrderHasDashboardLightRepository = workOrderHasDashboardLightRepository;
        this.carPictureService = carPictureService;
        this.workOrderHasMechanicService = workOrderHasMechanicService;
        this.entityFinder = entityFinder;
        this.workOrderValidator = workOrderValidator;

        this.baseDir = StorageUtils.toBaseDir(storageBaseDir);
    }

    @Transactional
    public CreateWorkOrderResponse createFull(CreateWorkOrderRequest request, List<MultipartFile> carPictures, MultipartFile signature) {
        workOrderValidator.validateOnCreate(request, carPictures == null ? 0 : carPictures.size(), signature != null && !signature.isEmpty());

        Record record = entityFinder.findByIdOrThrow(recordRepository, request.recordId(), "recordId", "Record id not found");

        WorkOrder workOrder = new WorkOrder(record, LocalDate.of(2025, 11, 15), LocalTime.of(9, 0), null);
        WorkOrder saved = workOrderRepository.save(workOrder);

        if (signature != null && !signature.isEmpty()) {
            try {
                String sigFilename = StorageUtils.saveMultipartFile(baseDir, "work-orders", signature);
                String sigPath = StorageUtils.publicPath("work-orders", sigFilename);
                saved.setSignaturePath(sigPath);
                workOrderRepository.save(saved);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to store signature file", e);
            }
        }

        if (carPictures != null) {
            for (MultipartFile file : carPictures) {
                if (file == null || file.isEmpty()) continue;
                CreateCarPictureRequest picReq = new CreateCarPictureRequest(file, saved.getId());
                carPictureService.createFromRequest(picReq);
            }
        }

        List<Integer> allServiceIds = new ArrayList<>();
        if (request.newServices() != null) {
            for (CreateWorkServiceRequest sReq : request.newServices()) {
                WorkService ws = new WorkService(
                        sReq.serviceName(),
                        LocalTime.parse(sReq.estimatedTime())
                );
                WorkService savedWs = workServiceRepository.save(ws);

                realizedServiceRepository.save(new WorkOrderRealizedService(saved, savedWs, false));
                allServiceIds.add(savedWs.getId());
            }
        }

        if (request.serviceIds() != null) {
            for (Integer sid : request.serviceIds()) {
                workServiceRepository.findById(sid).ifPresent(ws -> {
                    realizedServiceRepository.save(new WorkOrderRealizedService(saved, ws, false));
                    allServiceIds.add(sid);
                });
            }
        }

        if (request.dashboardLightsActive() != null) {
            for (var dlReq : request.dashboardLightsActive()) {
                dashboardLightRepository.findById(dlReq.dashboardLightId()).ifPresent(dl -> {
                    WorkOrderHasDashboardLight assoc = new WorkOrderHasDashboardLight(saved, dl, Boolean.TRUE.equals(dlReq.present()), Boolean.TRUE.equals(dlReq.operates()));
                    workOrderHasDashboardLightRepository.save(assoc);
                });
            }
        }

        Integer leaderId = request.leaderMechanicId();

        for (Integer mechanicId : request.mechanicIds()) {
            boolean isLeader = mechanicId.equals(leaderId);
            workOrderHasMechanicService.create(new CreateWorkOrderHasMechanicRequest(saved.getId(), mechanicId, isLeader));
        }

        return new CreateWorkOrderResponse(
                saved.getId(),
                record.getId(),
                saved.getEstimatedDate().toString(),
                saved.getEstimatedTime().toString(),
                saved.getSignaturePath()
        );
    }

    @Transactional(readOnly = true)
    public List<CreateWorkOrderResponse> getAll() {
        return workOrderRepository.findAll().stream()
                .map(w -> new CreateWorkOrderResponse(
                        w.getId(),
                        w.getRecord() == null ? null : w.getRecord().getId(),
                        w.getEstimatedDate() == null ? null : w.getEstimatedDate().toString(),
                        w.getEstimatedTime() == null ? null : w.getEstimatedTime().toString(),
                        w.getSignaturePath()
                ))
                .collect(Collectors.toList());
    }
}
