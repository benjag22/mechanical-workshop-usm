package com.mechanical_workshop_usm.mechanical_condition_module;

import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionRequest;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.CreateMechanicalConditionResponse;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.GroupedMechanicalCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.InteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ExteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ElectricalSystemCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.ElectricalSystemConditionRepository;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.ExteriorConditionRepository;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.InteriorConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<GroupedMechanicalCondition> getAllConditionsByType(MechanicalConditionType type) {
        List<GroupedMechanicalCondition.SingleMechanicalCondition> flat;

        switch (type) {
            case ELECTRICAL_SYSTEM -> {
                flat = electricalSystemConditionRepository.findAll().stream()
                    .map(e -> new GroupedMechanicalCondition.SingleMechanicalCondition(
                        e.getId(),
                        e.getPartConditionState()
                    ))
                    .toList();
            }
            case INTERIOR -> {
                flat = interiorConditionRepository.findAll().stream()
                    .map(i -> new GroupedMechanicalCondition.SingleMechanicalCondition(
                        i.getId(),
                        i.getPartConditionState()
                    ))
                    .toList();
            }
            case EXTERIOR -> {
                flat = exteriorConditionRepository.findAll().stream()
                    .map(ex -> new GroupedMechanicalCondition.SingleMechanicalCondition(
                        ex.getId(),
                        ex.getPartConditionState()
                    ))
                    .toList();
            }
            default -> throw new IllegalArgumentException("Unknown MechanicalConditionType: " + type);
        }

        return switch (type) {
            case ELECTRICAL_SYSTEM -> electricalSystemConditionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                    ElectricalSystemCondition::getPartName,
                    Collectors.mapping(
                        e -> new GroupedMechanicalCondition.SingleMechanicalCondition(
                            e.getId(),
                            e.getPartConditionState()
                        ),
                        Collectors.toList()
                    )
                ))
                .entrySet().stream()
                .map(entry -> new GroupedMechanicalCondition(entry.getKey(), entry.getValue()))
                .toList();

            case INTERIOR -> interiorConditionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                    InteriorCondition::getPartName,
                    Collectors.mapping(
                        i -> new GroupedMechanicalCondition.SingleMechanicalCondition(
                            i.getId(),
                            i.getPartConditionState()
                        ),
                        Collectors.toList()
                    )
                ))
                .entrySet().stream()
                .map(entry -> new GroupedMechanicalCondition(entry.getKey(), entry.getValue()))
                .toList();

            case EXTERIOR -> exteriorConditionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                    ExteriorCondition::getPartName,
                    Collectors.mapping(
                        ex -> new GroupedMechanicalCondition.SingleMechanicalCondition(
                            ex.getId(),
                            ex.getPartConditionState()
                        ),
                        Collectors.toList()
                    )
                ))
                .entrySet().stream()
                .map(entry -> new GroupedMechanicalCondition(entry.getKey(), entry.getValue()))
                .toList();

            default -> throw new IllegalArgumentException("Unknown MechanicalConditionType: " + type);
        };
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