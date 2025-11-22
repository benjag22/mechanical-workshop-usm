package com.mechanical_workshop_usm.work_order_realized_service_module;

import com.mechanical_workshop_usm.work_order_realized_service_module.dto.CreateWorkOrderRealizedServiceRequest;
import com.mechanical_workshop_usm.work_order_realized_service_module.dto.CreateWorkOrderRealizedServiceResponse;
import com.mechanical_workshop_usm.work_order_module.WorkOrder;
import com.mechanical_workshop_usm.work_order_module.WorkOrderRepository;
import com.mechanical_workshop_usm.work_service_module.WorkService;
import com.mechanical_workshop_usm.work_service_module.WorkServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class WorkOrderRealizedServiceService {

    private final WorkOrderRealizedServiceRepository repository;
    private final WorkOrderRepository workOrderRepository;
    private final WorkServiceRepository workServiceRepository;

    public WorkOrderRealizedServiceService(
            WorkOrderRealizedServiceRepository repository,
            WorkOrderRepository workOrderRepository,
            WorkServiceRepository workServiceRepository
    ) {
        this.repository = repository;
        this.workOrderRepository = workOrderRepository;
        this.workServiceRepository = workServiceRepository;
    }

    @Transactional
    public CreateWorkOrderRealizedServiceResponse create(CreateWorkOrderRealizedServiceRequest req) {
        WorkOrder wo = workOrderRepository.findById(req.workOrderId())
                .orElseThrow(() -> new IllegalArgumentException("WorkOrder not found: " + req.workOrderId()));

        WorkService ws = workServiceRepository.findById(req.workServiceId())
                .orElseThrow(() -> new IllegalArgumentException("WorkService not found: " + req.workServiceId()));

        if (repository.existsByWorkOrder_IdAndWorkService_Id(wo.getId(), ws.getId())) {
            throw new IllegalArgumentException("Realized service already exists for workOrderId=" + wo.getId() + " workServiceId=" + ws.getId());
        }

        WorkOrderRealizedService entity = new WorkOrderRealizedService(wo, ws, req.finalized());
        WorkOrderRealizedService saved = repository.save(entity);

        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public CreateWorkOrderRealizedServiceResponse getById(Integer id) {
        WorkOrderRealizedService w = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        return toDto(w);
    }

    @Transactional(readOnly = true)
    public List<CreateWorkOrderRealizedServiceResponse> getByWorkOrderId(Integer workOrderId) {
        return repository.findByWorkOrder_Id(workOrderId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CreateWorkOrderRealizedServiceResponse> getByWorkServiceId(Integer workServiceId) {
        return repository.findByWorkService_Id(workServiceId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CreateWorkOrderRealizedServiceResponse> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Transactional
    public CreateWorkOrderRealizedServiceResponse setFinalized(Integer id, boolean finalized) {
        WorkOrderRealizedService w = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        w.setFinalized(finalized);
        WorkOrderRealizedService saved = repository.save(w);
        return toDto(saved);
    }

    private CreateWorkOrderRealizedServiceResponse toDto(WorkOrderRealizedService w) {
        return new CreateWorkOrderRealizedServiceResponse(
                w.getId(),
                w.getWorkOrder().getId(),
                w.getWorkService().getId(),
                w.isFinalized()
        );
    }

    @Transactional
    public CreateWorkOrderRealizedServiceResponse toggleFinalized(Integer workOrderId, Integer workServiceId) {
        WorkOrderRealizedService entity = repository
                .findByWorkOrder_IdAndWorkService_Id(workOrderId, workServiceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No relationship was found in workOrderId=" + workOrderId + " workServiceId=" + workServiceId
                ));
        entity.setFinalized(!entity.isFinalized());
        WorkOrderRealizedService saved = repository.save(entity);
        return toDto(saved);
    }

    @Transactional
    public List<CreateWorkOrderRealizedServiceResponse> toggleFinalizedForServices(Integer workOrderId, List<Integer> workServiceIds) {
        List<CreateWorkOrderRealizedServiceResponse> responses = new ArrayList<>();
        for (Integer workServiceId : workServiceIds) {
            Optional<WorkOrderRealizedService> optEntity = repository.findByWorkOrder_IdAndWorkService_Id(workOrderId, workServiceId);
            if (optEntity.isPresent()) {
                WorkOrderRealizedService entity = optEntity.get();
                entity.setFinalized(!entity.isFinalized());
                WorkOrderRealizedService saved = repository.save(entity);
                responses.add(toDto(saved));
            }
        }
        return responses;
    }

}
