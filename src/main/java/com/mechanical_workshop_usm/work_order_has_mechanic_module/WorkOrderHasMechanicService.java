package com.mechanical_workshop_usm.work_order_has_mechanic_module;

import com.mechanical_workshop_usm.work_order_has_mechanic_module.dto.CreateWorkOrderHasMechanicRequest;
import com.mechanical_workshop_usm.work_order_has_mechanic_module.dto.GetWorkOrderHasMechanicResponse;
import com.mechanical_workshop_usm.work_order_module.WorkOrder;
import com.mechanical_workshop_usm.work_order_module.WorkOrderRepository;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfo;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderHasMechanicService {

    private final WorkOrderHasMechanicRepository repository;
    private final WorkOrderRepository workOrderRepository;
    private final MechanicInfoRepository mechanicInfoRepository;

    public WorkOrderHasMechanicService(
            WorkOrderHasMechanicRepository repository,
            WorkOrderRepository workOrderRepository,
            MechanicInfoRepository mechanicInfoRepository
    ) {
        this.repository = repository;
        this.workOrderRepository = workOrderRepository;
        this.mechanicInfoRepository = mechanicInfoRepository;
    }

    @Transactional
    public GetWorkOrderHasMechanicResponse create(CreateWorkOrderHasMechanicRequest req) {
        WorkOrder wo = workOrderRepository.findById(req.workOrderId())
                .orElseThrow(() -> new IllegalArgumentException("WorkOrder not found: " + req.workOrderId()));

        MechanicInfo mi = mechanicInfoRepository.findById(req.mechanicInfoId())
                .orElseThrow(() -> new IllegalArgumentException("MechanicInfo not found: " + req.mechanicInfoId()));

        if (repository.existsByWorkOrder_IdAndMechanicInfo_Id(wo.getId(), mi.getId())) {
            throw new IllegalArgumentException("Association already exists for workOrderId=" + wo.getId() + " mechanicInfoId=" + mi.getId());
        }

        WorkOrderHasMechanic assoc = new WorkOrderHasMechanic(wo, mi, Boolean.TRUE.equals(req.isLeader()));
        WorkOrderHasMechanic saved = repository.save(assoc);

        return new GetWorkOrderHasMechanicResponse(saved.getId(), wo.getId(), mi.getId(), saved.isLeader());
    }

    @Transactional(readOnly = true)
    public GetWorkOrderHasMechanicResponse getById(Integer id) {
        WorkOrderHasMechanic w = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found: " + id));
        return new GetWorkOrderHasMechanicResponse(w.getId(), w.getWorkOrder().getId(), w.getMechanicInfo().getId(), w.isLeader());
    }

    @Transactional(readOnly = true)
    public List<GetWorkOrderHasMechanicResponse> getByWorkOrderId(Integer workOrderId) {
        return repository.findByWorkOrder_Id(workOrderId).stream()
                .map(w -> new GetWorkOrderHasMechanicResponse(w.getId(), w.getWorkOrder().getId(), w.getMechanicInfo().getId(), w.isLeader()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetWorkOrderHasMechanicResponse> getAll() {
        return repository.findAll().stream()
                .map(w -> new GetWorkOrderHasMechanicResponse(w.getId(), w.getWorkOrder().getId(), w.getMechanicInfo().getId(), w.isLeader()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}