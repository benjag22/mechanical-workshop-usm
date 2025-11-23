package com.mechanical_workshop_usm.record_module.record_state.service.CheckOut;


import com.mechanical_workshop_usm.record_module.record.Record;
import com.mechanical_workshop_usm.record_module.record.RecordRepository;
import com.mechanical_workshop_usm.record_module.record.RecordValidator;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_out.CreateCheckOutRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_out.CheckOutResponse;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckOut;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.CheckOutRepository;
import com.mechanical_workshop_usm.util.EntityFinder;
import com.mechanical_workshop_usm.work_order_module.WorkOrder;
import com.mechanical_workshop_usm.work_order_module.WorkOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CheckOutService {
    private final CheckOutRepository repository;
    private final RecordRepository recordRepository;
    private final CheckOutValidator checkOutValidator;
    private final EntityFinder entityFinder;
    private final WorkOrderRepository workOrderRepository;

    public CheckOutService(CheckOutRepository repository, RecordRepository recordRepository, EntityFinder entityFinder, WorkOrderRepository workOrderRepository, CheckOutValidator checkOutValidator, RecordValidator recordValidator) {
        this.repository = repository;
        this.recordRepository = recordRepository;
        this.checkOutValidator = checkOutValidator;
        this.entityFinder = entityFinder;
        this.workOrderRepository = workOrderRepository;
    }

    @Transactional
    public CheckOutResponse create(CreateCheckOutRequest request) {
        WorkOrder workOrder = entityFinder.findByIdOrThrow(
            workOrderRepository,
            request.workOrderId(),
            "workOrderId",
            "workOrderId not found"
        );
        checkOutValidator.validateOnCreate(request,  workOrder.getRecord().getId());


        CheckOut checkOut = new CheckOut(request.vehicleDiagnosis(), LocalDateTime.now(), request.mileage(), workOrder.getRecord());

        CheckOut saved = repository.save(checkOut);

        return new CheckOutResponse(
            saved.getId(),
            workOrder.getId(),
            saved.getEntryTime(),
            saved.getMileage(),
            saved.getVehicleDiagnosis()
        );
    }

    @Transactional(readOnly = true)
    public CheckOutResponse getById(int id) {
        CheckOut c = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("CheckOut not found"));
        return new CheckOutResponse(
            c.getId(),
            c.getRecord().getId(),
            c.getEntryTime(),
            c.getMileage(),
            c.getVehicleDiagnosis()
        );
    }


}
