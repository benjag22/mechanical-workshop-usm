package com.mechanical_workshop_usm.work_order_module;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car.dto.GetCar;
import com.mechanical_workshop_usm.client_info_module.dtos.Client;
import com.mechanical_workshop_usm.image_module.image.ImageRepository;
import com.mechanical_workshop_usm.image_module.image.ImageService;
import com.mechanical_workshop_usm.image_module.image.dto.GetImage;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfo;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoRepository;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoService;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicRequest;
import com.mechanical_workshop_usm.mechanic_info_module.dto.CreateMechanicResponse;
import com.mechanical_workshop_usm.util.EntityFinder;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.WorkOrderHasDashboardLight;
import com.mechanical_workshop_usm.work_order_has_dashboard_light_module.WorkOrderHasDashboardLightRepository;
import com.mechanical_workshop_usm.work_order_has_mechanic_module.WorkOrderHasMechanic;
import com.mechanical_workshop_usm.work_order_has_mechanic_module.WorkOrderHasMechanicService;
import com.mechanical_workshop_usm.work_order_has_mechanic_module.dto.CreateWorkOrderHasMechanicRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderRequest;
import com.mechanical_workshop_usm.work_order_module.dto.CreateWorkOrderResponse;
import com.mechanical_workshop_usm.work_order_module.dto.GetWorkOrderFull;
import com.mechanical_workshop_usm.work_order_module.dto.TrimmedWorkOrder;
import com.mechanical_workshop_usm.work_order_module.projections.TrimmedWorkOrderInfoProjection;
import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedService;
import com.mechanical_workshop_usm.work_order_realized_service_module.WorkOrderRealizedServiceRepository;
import com.mechanical_workshop_usm.work_service_module.WorkService;
import com.mechanical_workshop_usm.work_service_module.WorkServiceRepository;
import com.mechanical_workshop_usm.work_service_module.dto.CreateWorkServiceRequest;
import com.mechanical_workshop_usm.record_module.record.Record;
import com.mechanical_workshop_usm.record_module.record.RecordRepository;

import com.mechanical_workshop_usm.work_service_module.dto.GetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
    private final ImageService imageService;
    private final MechanicInfoRepository mechanicInfoRepository;

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
        MechanicInfoService mechanicInfoService,
        MechanicInfoRepository mechanicInfoRepository) {
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
        this.mechanicInfoService = mechanicInfoService;
        this.mechanicInfoRepository = mechanicInfoRepository;
    }

    public List<TrimmedWorkOrder> getTrimmedWorkOrders() {
        List<TrimmedWorkOrderInfoProjection> projections = workOrderRepository.findTrimmedWorkOrders();

        return projections.stream()
            .map(projection -> new TrimmedWorkOrder(
                projection.getWorkOrderId(),
                projection.isCompleted(),
                projection.getCreatedAt(),
                projection.getEstimatedDeliveryTime(),
                projection.getSignaturePath(),
                projection.getMechanicLeaderFullName(),
                projection.getClientFirstName(),
                projection.getClientLastName(),
                projection.getClientCellphoneNumber(),
                projection.getCarLicensePlate()
            ))
            .toList();
    }

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = List.of("jpg", "jpeg", "png");

    private boolean isAllowedImageExtension(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return false;
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
        return ALLOWED_IMAGE_EXTENSIONS.contains(extension);
    }
    public List<FieldErrorResponse> validateImageFormats(List<MultipartFile> carPictures, MultipartFile signature) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        if (signature != null && !signature.isEmpty() && !isAllowedImageExtension(signature)) {
            errors.add(new FieldErrorResponse(
                    "signature",
                    "Only PNG, JPG or JPEG images are allowed for signature"
            ));
        }

        if (carPictures != null && !carPictures.isEmpty()) {
            int idx = 0;
            for (MultipartFile photo : carPictures) {
                if (photo != null && !photo.isEmpty() && !isAllowedImageExtension(photo)) {
                    errors.add(new FieldErrorResponse(
                            "carPictures[" + idx + "]",
                            "Only PNG, JPG or JPEG images are allowed for car pictures"
                    ));
                }
                idx++;
            }
        }
        return errors;
    }

    @Transactional
    public CreateWorkOrderResponse createFull(CreateWorkOrderRequest request, List<MultipartFile> carPictures,
                                              MultipartFile signature) {
        workOrderValidator.validateOnCreate(request, carPictures == null ? 0 : carPictures.size(),
            signature != null && !signature.isEmpty());

        Record record = entityFinder.findByIdOrThrow(
                recordRepository,
                request.recordId(),
                "recordId",
                "Record id not" + " found"
        );

        WorkOrder workOrder = new WorkOrder(record, LocalDateTime.now(), LocalDateTime.now(), null);
        WorkOrder saved = workOrderRepository.save(workOrder);

        List<FieldErrorResponse> errors = validateImageFormats(carPictures, signature);
        if (!errors.isEmpty()) {
            throw new MultiFieldException("Image format validation failed", errors);
        }

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
        } else {
            leaderId = request.leaderMechanicId();
            mechanicIdsList.add(leaderId);
        }

        if (request.newMechanics() != null && !request.newMechanics().isEmpty()) {
            for (CreateMechanicRequest newMechanic : request.newMechanics()) {
                CreateMechanicResponse mechanic =
                    mechanicInfoService.createMechanic(new CreateMechanicRequest(newMechanic.name(),
                        newMechanic.rut()));
                mechanicIdsList.add(mechanic.id());
            }
        }

        if(request.mechanicIds() != null) {
            mechanicIdsList.addAll(request.mechanicIds());
        }

        for (Integer mechanicId : mechanicIdsList) {
            boolean isLeader = mechanicId.equals(leaderId);
            workOrderHasMechanicService.create(new CreateWorkOrderHasMechanicRequest(saved.getId(), mechanicId,
                isLeader));
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
                if (dlReq.present()) {
                    imageRepository.findById(dlReq.dashboardLightId()).ifPresent(dl -> {
                        WorkOrderHasDashboardLight assoc = new WorkOrderHasDashboardLight(saved, dl, dlReq.isFunctional());
                        workOrderHasDashboardLightRepository.save(assoc);
                    });
                }
            }
        }


        return new CreateWorkOrderResponse(
            saved.getId(),
            record.getId(),
            saved.getCreatedAt().toString(),
            saved.getEstimatedDelivery().toString(),
            saved.getSignaturePath()
        );
    }


    @Transactional(readOnly = true)
    public GetWorkOrderFull getFullById(Integer id) {
        WorkOrder wo = entityFinder.findByIdOrThrow(
            workOrderRepository, id, "workOrderId", "WorkOrder not found"
        );

        String createdAt = wo.getCreatedAt().toString();
        String estimatedDelivery = wo.getEstimatedDelivery().toString();

        List<GetWorkOrderFull.GetServiceState> services = realizedServiceRepository.findByWorkOrder_Id(id).stream()
                .map(rs -> {
                    WorkService ws = rs.getWorkService();
                    return new GetWorkOrderFull.GetServiceState(
                            ws.getId(),
                            ws.getServiceName(),
                            ws.getEstimatedTime().toString(),
                            rs.isFinalized()
                    );
                }).toList();

        List<GetWorkOrderFull.GetMechanicWorkOrder> mechanics =
                workOrderHasMechanicService.getByWorkOrderId(id).stream()
                        .map(m -> {
                            MechanicInfo mech = mechanicInfoService.getMechanicById(m.mechanicInfoId());
                            return new GetWorkOrderFull.GetMechanicWorkOrder(
                                    mech.getId(),
                                    mech.getName(),
                                    mech.getRut(),
                                    m.isLeader()
                            );
                        }).toList();

        String signature = wo.getSignaturePath();

        List<GetImage> carImages = imageService.getCarImagesByWorkOrderId(wo.getId());

        List<GetWorkOrderFull.GetWorkOrderHasDashboardLight> dashboardLights =
            workOrderHasDashboardLightRepository.findByWorkOrder_Id(id).stream()
            .map(dl -> new GetWorkOrderFull.GetWorkOrderHasDashboardLight(
                    dl.getDashboardLight().getId(),
                    imageService.buildPublicUrl(dl.getDashboardLight().getPath()),
                    dl.getDashboardLight().getAlt(),
                    dl.isFunctional()
            )).toList();


        Record record = wo.getRecord();


        Client customer = new Client(
            record.getClientInfo().getId(),
            record.getClientInfo().getFirstName(),
            record.getClientInfo().getRut(),
            record.getClientInfo().getLastName(),
            record.getClientInfo().getEmailAddress(),
            record.getClientInfo().getAddress(),
            record.getClientInfo().getCellphoneNumber()
        );

        GetCar vehicle = new GetCar(
            record.getCar().getId(),
            record.getCar().getVIN(),
            record.getCar().getLicensePlate(),
            record.getCar().getCarModel().getId(),
            record.getCar().getCarModel().getModelName(),
            record.getCar().getCarModel().getModelType(),
            record.getCar().getCarModel().getModelYear(),
            record.getCar().getCarModel().getBrand().getId(),
            record.getCar().getCarModel().getBrand().getBrandName()
            );

        return new GetWorkOrderFull(

            wo.getId(),
            wo.getCreatedAt() == null ? null : wo.getCreatedAt().toString(),
            wo.getEstimatedDelivery() == null ? null : wo.getEstimatedDelivery().toString(),
            services,
            mechanics,
            wo.getSignaturePath(),
            carImages,
            dashboardLights,
            customer,
            vehicle
        );
    }
    public boolean isLeaderAuthorized(Integer id, String rut) {
        return Optional.ofNullable(workOrderHasMechanicService.getByIdAndMechanicRut(id, rut))
            .map(WorkOrderHasMechanic::isLeader)
            .orElse(false);
    }
}
