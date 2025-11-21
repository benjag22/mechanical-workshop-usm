package com.mechanical_workshop_usm.work_order_module;

import com.mechanical_workshop_usm.image_module.image.ImageRepository;
import com.mechanical_workshop_usm.image_module.image.ImageService;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoService;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicResponse;
import com.mechanical_workshop_usm.util.EntityFinder;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.WorkOrderHasDashboardLight;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.WorkOrderHasDashboardLightRepository;
import com.mechanical_workshop_usm.work_order_has_mechanic_module.WorkOrderHasMechanicService;
import com.mechanical_workshop_usm.work_order_has_mechanic_module.dto.CreateWorkOrderHasMechanicRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderResponse;
import com.mechanical_workshop_usm.work_order_module.dto.TrimmedWorkOrder;
import com.mechanical_workshop_usm.work_order_module.projections.TrimmedWorkOrderInfoProjection;
import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedService;
import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedServiceRepository;
import com.mechanical_workshop_usm.work_service_module.WorkService;
import com.mechanical_workshop_usm.work_service_module.WorkServiceRepository;
import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import com.mechanical_workshop_usm.record_module.record.Record;
import com.mechanical_workshop_usm.record_module.record.RecordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final RecordRepository recordRepository;
    private final WorkServiceRepository workServiceRepository;
    private final WorkOrderRealizedServiceRepository realizedServiceRepository;
    private final WorkOrderHasDashboardLightRepository workOrderHasDashboardLightRepository;

    private final WorkOrderHasMechanicService workOrderHasMechanicService;
    private final EntityFinder entityFinder;
    private final WorkOrderValidator workOrderValidator;

    private final MechanicInfoService mechanicInfoService;


    private final ImageRepository imageRepository;
    private  final ImageService imageService;

    public WorkOrderService(
            WorkOrderRepository workOrderRepository,
            RecordRepository recordRepository,
            WorkServiceRepository workServiceRepository,
            WorkOrderRealizedServiceRepository realizedServiceRepository,
            WorkOrderHasDashboardLightRepository workOrderHasDashboardLightRepository,
            ImageRepository imageRepository,
            WorkOrderHasMechanicService workOrderHasMechanicService,
            EntityFinder entityFinder,
            WorkOrderValidator workOrderValidator,
            ImageService imageService,
            MechanicInfoService mechanicInfoService
    ) {
        this.workOrderRepository = workOrderRepository;
        this.recordRepository = recordRepository;
        this.workServiceRepository = workServiceRepository;
        this.realizedServiceRepository = realizedServiceRepository;
        this.workOrderHasDashboardLightRepository = workOrderHasDashboardLightRepository;
        this.workOrderHasMechanicService = workOrderHasMechanicService;
        this.entityFinder = entityFinder;
        this.workOrderValidator = workOrderValidator;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
        this.mechanicInfoService =  mechanicInfoService;
    }

    public List<TrimmedWorkOrder> getTrimmedWorkOrders(){
        List<TrimmedWorkOrderInfoProjection> projections = workOrderRepository.findTrimmedWorkOrders();

        return projections.stream()
            .map(projection -> new TrimmedWorkOrder(
                projection.getWorkOrderId(),
                projection.isCompleted(),
                projection.getEstimatedTime(),
                projection.getSignaturePath(),
                projection.getMechanicLeaderFullName(),
                projection.getClientFirstName(),
                projection.getClientLastName(),
                projection.getClientCellphoneNumber(),
                projection.getCarLicensePlate()
            ))
            .toList();
    }
    @Transactional
    public CreateWorkOrderResponse createFull(CreateWorkOrderRequest request, List<MultipartFile> carPictures, MultipartFile signature) {
        workOrderValidator.validateOnCreate(request, carPictures == null ? 0 : carPictures.size(), signature != null && !signature.isEmpty());

        Record record = entityFinder.findByIdOrThrow(recordRepository, request.recordId(), "recordId", "Record id not found");

        WorkOrder workOrder = new WorkOrder(record, LocalDate.of(2025, 11, 15), LocalTime.of(9, 0), null);
        WorkOrder saved = workOrderRepository.save(workOrder);


        AtomicReference<List<Integer>> allServiceIds = new AtomicReference<>(new ArrayList<>());
        if (request.newServices() != null) {
            for (CreateWorkServiceRequest sReq : request.newServices()) {
                WorkService ws = new WorkService(
                        sReq.serviceName(),
                        LocalTime.parse(sReq.estimatedTime())
                );
                WorkService savedWs = workServiceRepository.save(ws);

                realizedServiceRepository.save(new WorkOrderRealizedService(saved, savedWs, false));
                allServiceIds.get().add(savedWs.getId());
            }
        }
        if (request.serviceIds() != null) {
            for (Integer sid : request.serviceIds()) {
                workServiceRepository.findById(sid).ifPresent(ws -> {
                    realizedServiceRepository.save(new WorkOrderRealizedService(saved, ws, false));
                    allServiceIds.get().add(sid);
                });
            }
        }

        // Apartado de mecanicos
        List<Integer> mechanicIdsList = new ArrayList<>();
        Integer leaderId;

        if (request.newLeaderMechanic() != null) {
            CreateMechanicResponse mechanic = mechanicInfoService.createMechanic(new CreateMechanicRequest(
                    request.newLeaderMechanic().name(),
                    request.newLeaderMechanic().rut()
            ));
            leaderId = mechanic.id();
            mechanicIdsList.add(leaderId);
        }
        else {
            leaderId = request.leaderMechanicId();
            mechanicIdsList.add(leaderId);
        }

        if (request.newMechanics() != null &&  !request.newMechanics().isEmpty()) {
            for (CreateMechanicRequest newMechanic : request.newMechanics()) {
                CreateMechanicResponse mechanic = mechanicInfoService.createMechanic(new CreateMechanicRequest(newMechanic.name(), newMechanic.rut()));
                mechanicIdsList.add(mechanic.id());
            }
        }

        mechanicIdsList.addAll(request.mechanicIds());

        for (Integer mechanicId : mechanicIdsList) {
            boolean isLeader = mechanicId.equals(leaderId);
            workOrderHasMechanicService.create(new CreateWorkOrderHasMechanicRequest(saved.getId(), mechanicId, isLeader));
        }


        // Apartado de imagenes
        if (signature != null && !signature.isEmpty()) {
            String sigFilename = imageService.saveSignature(signature, workOrder.getId());
            String sigPath = imageService.buildPublicUrl(sigFilename);
            saved.setSignaturePath(sigPath);
            workOrderRepository.save(saved);
        }
        if (carPictures != null && !carPictures.isEmpty()) {
            for (MultipartFile file : carPictures) {
                if (file == null || file.isEmpty()) continue;
                imageService.saveCarImage(saved.getId(), file, file.getOriginalFilename());
            }
        }
        if (request.dashboardLightsActive() != null) {
            for (var dlReq : request.dashboardLightsActive()) {
                imageRepository.findById(dlReq.dashboardLightId()).ifPresent(dl -> {
                    WorkOrderHasDashboardLight assoc = new WorkOrderHasDashboardLight(saved, dl, Boolean.TRUE.equals(dlReq.isFunctional()));
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
