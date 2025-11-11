package com.mechanical_workshop_usm.mechanical_condition_module;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionRequest;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionResponse;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.SingleMechanicalCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.InteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ExteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ElectricalSystemCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.ElectricalSystemConditionRepository;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.ExteriorConditionRepository;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.InteriorConditionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MechanicalConditionService {
    private final MechanicalConditionValidator validator;
    private final ElectricalSystemConditionRepository electricalSystemConditionRepository;
    private final InteriorConditionRepository interiorConditionRepository;
    private final ExteriorConditionRepository exteriorConditionRepository;

    public MechanicalConditionService(
            ElectricalSystemConditionRepository electricalSystemConditionRepository,
            InteriorConditionRepository interiorConditionRepository,
            ExteriorConditionRepository exteriorConditionRepository,
            MechanicalConditionValidator validator) {

        this.electricalSystemConditionRepository = electricalSystemConditionRepository;
        this.interiorConditionRepository = interiorConditionRepository;
        this.exteriorConditionRepository = exteriorConditionRepository;
        this.validator = validator;
    }
    public List<SingleMechanicalCondition> getAllConditionsByType(MechanicalConditionType type) {
        switch (type) {
            case ELECTRICAL_SYSTEM -> {
                List<SingleMechanicalCondition> response = new ArrayList<>();
                List<ElectricalSystemCondition> electricalSystemConditions = electricalSystemConditionRepository.findAll();
                for(ElectricalSystemCondition electricalSystemCondition : electricalSystemConditions) {
                    response.add(
                        new SingleMechanicalCondition(
                            electricalSystemCondition.getId(),
                            electricalSystemCondition.getPartName(),
                            electricalSystemCondition.getPartConditionState()
                        )
                    );
                }
                return response;

            }
            case INTERIOR -> {
                List<SingleMechanicalCondition> response = new ArrayList<>();
                List<InteriorCondition> interiorConditions = interiorConditionRepository.findAll();
                for(InteriorCondition interiorCondition : interiorConditions) {
                    response.add(
                        new SingleMechanicalCondition(
                            interiorCondition.getId(),
                            interiorCondition.getPartName(),
                            interiorCondition.getPartConditionState()
                        )
                    );
                }
                return response;
            }

            case EXTERIOR -> {
                List<SingleMechanicalCondition> response = new ArrayList<>();
                List<ExteriorCondition> exteriorConditions = exteriorConditionRepository.findAll();
                for(ExteriorCondition exteriorCondition : exteriorConditions) {
                    response.add(
                        new SingleMechanicalCondition(
                            exteriorCondition.getId(),
                            exteriorCondition.getPartName(),
                            exteriorCondition.getPartConditionState()
                        )
                    );
                }
                return response;
            }

            default -> throw new IllegalArgumentException("Unknown MechanicalConditionType: " + type);
        }
    }

    public CreateMechanicalConditionResponse createMechanicalCondition(CreateMechanicalConditionRequest request) {
        validator.validateOnCreate(request);

        switch (request.conditionType().toLowerCase()) {
            case "interior" -> {

                InteriorCondition interiorCondition = InteriorCondition.builder()
                        .partConditionState(request.partConditionState())
                        .partName(request.partName())
                        .build();
                InteriorCondition saved = interiorConditionRepository.save(interiorCondition);

                return new CreateMechanicalConditionResponse(
                        saved.getId(),
                        saved.getPartName(),
                        saved.getPartConditionState(),
                        request.conditionType().toLowerCase()
                );
            }
            case "exterior" -> {
                ExteriorCondition exteriorCondition = ExteriorCondition.builder()
                        .partConditionState(request.partConditionState())
                        .partName(request.partName())
                        .build();
                ExteriorCondition saved = exteriorConditionRepository.save(exteriorCondition);

                return new CreateMechanicalConditionResponse(
                        saved.getId(),
                        saved.getPartName(),
                        saved.getPartConditionState(),
                        request.conditionType().toLowerCase()
                );
            }
            case "electrical" -> {
                ElectricalSystemCondition electricalSystemCondition = ElectricalSystemCondition.builder()
                        .partConditionState(request.partConditionState())
                        .partName(request.partName())
                        .build();
                ElectricalSystemCondition saved = electricalSystemConditionRepository.save(electricalSystemCondition);

                return new CreateMechanicalConditionResponse(
                        saved.getId(),
                        saved.getPartName(),
                        saved.getPartConditionState(),
                        request.conditionType().toLowerCase()
                );
            }
            default -> throw new IllegalArgumentException("Invalid condition type");
        }
    }

}