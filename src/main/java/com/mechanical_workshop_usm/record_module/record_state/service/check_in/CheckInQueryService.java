package com.mechanical_workshop_usm.record_module.record_state.service.check_in;

import com.mechanical_workshop_usm.check_in_consider_conditions_module.CheckInConsiderConditions;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.GetCheckInBasicResponse;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.MechanicalCondition;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.CheckInRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckInQueryService {

    private final CheckInRepository checkInRepository;

    public CheckInQueryService(CheckInRepository checkInRepository) {
        this.checkInRepository = checkInRepository;
    }

    @Transactional(readOnly = true)
    public List<GetCheckInBasicResponse> getAllCheckInsFull() {
        List<CheckIn> checkIns = checkInRepository.findAll();
        return checkIns.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private GetCheckInBasicResponse mapToDto(CheckIn ci) {
        Integer checkInId = ci.getId();

        String clientName = null;
        String clientEmail = null;
        String brandName = null;
        String modelName = null;
        String modelType = null;
        Integer modelYear = null;
        String licensePlate = null;
        String reason = null;
        String observations = null;

        if (ci.getRecord() != null) {
            var record = ci.getRecord();

            var client = record.getClientInfo();
            if (client != null) {
                String first = client.getFirstName() == null ? "" : client.getFirstName();
                String last = client.getLastName() == null ? "" : client.getLastName();
                clientName = (first + (last.isBlank() ? "" : " " + last)).trim();
                clientEmail = client.getEmailAddress();
            }

            var car = record.getCar();
            if (car != null) {
                licensePlate = car.getLicensePlate();
                var model = car.getCarModel();
                if (model != null) {
                    modelName = model.getModelName();
                    modelType = model.getModelType();
                    modelYear = model.getModelYear();
                    var brand = model.getBrand();
                    if (brand != null) {
                        brandName = brand.getBrandName();
                    }
                }
            }

            reason = record.getReason();
        }

        List<MechanicalCondition> conditions = new ArrayList<>();
        if (ci.getMechanicalConditions() != null) {
            for (CheckInConsiderConditions cc : ci.getMechanicalConditions()) {
                if (cc == null) continue;

                if (cc.getExteriorCondition() != null) {
                    var ec = cc.getExteriorCondition();
                    conditions.add(new  MechanicalCondition(
                        ec.getPartName() == null ? "" : ec.getPartName(),
                        ec.getPartConditionState() == null ? "" : ec.getPartConditionState()
                    ));
                }

                if (cc.getInteriorCondition() != null) {
                    var ic = cc.getInteriorCondition();
                    conditions.add(new  MechanicalCondition(
                        ic.getPartName() == null ? "" : ic.getPartName(),
                        ic.getPartConditionState() == null ? "" : ic.getPartConditionState()
                    ));
                }

                if (cc.getElectricalSystemCondition() != null) {
                    var el = cc.getElectricalSystemCondition();
                    conditions.add(new  MechanicalCondition(
                        el.getPartName() == null ? "" : el.getPartName(),
                        el.getPartConditionState() == null ? "" : el.getPartConditionState()
                    ));
                }
            }
        }

        observations = ci.getObservations();

        String entryTime = ci.getEntryTime() != null ? ci.getEntryTime().toString() : null;
        return new GetCheckInBasicResponse(
                checkInId,
                clientName,
                clientEmail,
                brandName,
                modelName,
                modelType,
                modelYear,
                licensePlate,
                reason,
                observations,
                conditions,
                entryTime
        );
    }
}