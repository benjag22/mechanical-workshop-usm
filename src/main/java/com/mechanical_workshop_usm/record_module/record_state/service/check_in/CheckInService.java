package com.mechanical_workshop_usm.record_module.record_state.service.check_in;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car.Car;
import com.mechanical_workshop_usm.car_module.car.CarRepository;
import com.mechanical_workshop_usm.car_module.car.CarService;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarCheckInRequest;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarRequest;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarResponse;
import com.mechanical_workshop_usm.car_module.car_model.CarModelService;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelRequest;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelResponse;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrandService;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandResponse;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelCheckInRequest;
import com.mechanical_workshop_usm.client_info_module.ClientInfo;
import com.mechanical_workshop_usm.client_info_module.ClientInfoRepository;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientResponse;
import com.mechanical_workshop_usm.client_info_module.ClientInfoService;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.MechanicalCondition;
import com.mechanical_workshop_usm.record_module.record.Record;
import com.mechanical_workshop_usm.record_module.record.RecordRepository;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInResponse;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.GetCheckInBasicResponse;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.GetCheckInFullResponse;
import com.mechanical_workshop_usm.mechanical_condition_module.dto.MechanicalConditionInfo;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckOut;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.GasLevel;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.RecordState;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.CheckInRepository;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.CheckOutRepository;
import com.mechanical_workshop_usm.record_module.record_state.service.RecordStateValidator;
import com.mechanical_workshop_usm.tool_module.Tool;
import com.mechanical_workshop_usm.tool_module.ToolRepository;
import com.mechanical_workshop_usm.tool_module.ToolService;
import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;
import com.mechanical_workshop_usm.util.EntityFinder;
import com.mechanical_workshop_usm.check_in_consider_conditions_module.CheckInConsiderConditions;
import com.mechanical_workshop_usm.check_in_has_tools_module.CheckInHaveTool;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ExteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.InteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ElectricalSystemCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.ExteriorConditionRepository;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.InteriorConditionRepository;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.repository.ElectricalSystemConditionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckInService {
    private final CheckInRepository checkInRepository;
    private final CheckOutRepository checkOutRepository;
    private final RecordRepository recordRepository;
    private final CarRepository carRepository;
    private final ClientInfoRepository clientInfoRepository;
    private final CheckInValidator checkInValidator;
    private final EntityFinder entityFinder;
    private final ClientInfoService clientInfoService;
    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final CarService carService;
    private final RecordStateValidator recordStateValidator;
    private final ToolRepository toolRepository;
    private final ToolService toolService;
    private final ExteriorConditionRepository exteriorConditionRepository;
    private final InteriorConditionRepository interiorConditionRepository;
    private final ElectricalSystemConditionRepository electricalSystemConditionRepository;

    public CheckInService(
        CheckOutRepository checkOutRepository,
        CheckInRepository checkInRepository,
        RecordRepository recordRepository,
        CarRepository carRepository,
        ClientInfoRepository clientInfoRepository,
        CheckInValidator checkInValidator,
        EntityFinder entityFinder,
        ClientInfoService clientInfoService,
        CarBrandService carBrandService,
        CarModelService carModelService,
        CarService carService,
        RecordStateValidator recordStateValidator,
        ToolRepository toolRepository,
        ToolService toolService,
        ExteriorConditionRepository exteriorConditionRepository,
        InteriorConditionRepository interiorConditionRepository,
        ElectricalSystemConditionRepository electricalSystemConditionRepository) {
        this.checkOutRepository = checkOutRepository;
        this.checkInRepository = checkInRepository;
        this.recordRepository = recordRepository;
        this.carRepository = carRepository;
        this.clientInfoRepository = clientInfoRepository;
        this.checkInValidator = checkInValidator;
        this.entityFinder = entityFinder;
        this.clientInfoService = clientInfoService;
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.carService = carService;
        this.recordStateValidator = recordStateValidator;
        this.toolRepository = toolRepository;
        this.toolService = toolService;
        this.exteriorConditionRepository = exteriorConditionRepository;
        this.interiorConditionRepository = interiorConditionRepository;
        this.electricalSystemConditionRepository = electricalSystemConditionRepository;
    }

    @Transactional
    public CreateCheckInResponse createCheckIn(CreateCheckInRequest request) {
        checkInValidator.validateOnCreate(request);
        if (request.clientId() == null || request.clientId() <= 0) {
            clientInfoService.validateOnCreate(request.client());
        }
        if (request.carId() == null) {
            carService.validate(request.car());
            if (request.carModelID() == null) {
                carModelService.validate(request.carModel());
                if (request.carBrandID() == null) {
                    carBrandService.validate(request.carBrand());
                }
            }
        }
        else {
            Optional<Record> lastRecordOpt = recordRepository.findFirstByCar_IdOrderByIdDesc(request.carId());
            if (lastRecordOpt.isPresent()) {
                Record lastRecord = lastRecordOpt.get();

                boolean hasCheckIn = checkInRepository.findByRecord_Id(lastRecord.getId()).isPresent();
                boolean hasCheckOut = checkOutRepository.findByRecord_Id(lastRecord.getId()).isPresent();

                if (hasCheckIn && !hasCheckOut) {
                    List<FieldErrorResponse> errors = List.of(
                        new FieldErrorResponse("car_id", "The car has a pending check-in or work order; a check-out must be issued first to continue.")
                    );
                    throw new MultiFieldException("Cannot create new check-in", errors);
                }
            }
        }
        if (request.newTools() != null) {
            for (CreateToolRequest tool : request.newTools() ) {
                toolService.validateOnCreate(tool);
            }
        }
        if (request.reason() == null) {
            List<FieldErrorResponse> errors = new ArrayList<>();
            errors.add(new FieldErrorResponse("reason", "reason is null or empty"));
            throw new MultiFieldException("Invalid check-in fields", errors);
        }
        recordStateValidator.validateOnCreate(request.recordState());

        ClientInfo clientInfo;
        if (request.clientId() != null && request.clientId() > 0) {
            clientInfo = entityFinder.findByIdOrThrow(clientInfoRepository, request.clientId(), "client_id", "Client not found");
        }
        else {
            CreateClientResponse createdClient = clientInfoService.createClient(request.client());
            clientInfo = entityFinder.findByIdOrThrow(clientInfoRepository, createdClient.id(), "client", "Client not found after creation");
        }

        Car car;
        if (request.carId() != null) {
            car = entityFinder.findByIdOrThrow(carRepository, request.carId(), "car_id", "Car not found");
        } else {
            Integer modelId = null;

            if (request.carModelID() != null) {
                modelId = request.carModelID();

                if (request.carBrandID() != null || request.carBrand() != null) {
                    throw new MultiFieldException("Invalid car/model/brand combination",
                            List.of(new FieldErrorResponse("car_brand", "When providing carModelID you must not provide carBrandID or carBrand")));
                }

                if (request.car() == null) {
                    throw new MultiFieldException("Invalid car",
                            List.of(new FieldErrorResponse("car", "Car object is required when creating a car from carModelID")));
                }
            }
            else if (request.carModel() != null) {
                Integer brandId = null;

                if (request.carBrandID() != null) {
                    brandId = request.carBrandID();
                } else if (request.carBrand() != null) {
                    CreateCarBrandRequest brandRequest = request.carBrand();
                    CreateCarBrandResponse createdBrand = carBrandService.createBrand(brandRequest);
                    brandId = createdBrand.id();
                } else {
                    throw new MultiFieldException("Invalid car brand",
                            List.of(new FieldErrorResponse("car_brand", "carBrandID or carBrand is required when creating carModel")));
                }

                CreateCarModelCheckInRequest incomingModel = request.carModel();
                CreateCarModelRequest modelRequest = new CreateCarModelRequest(
                        incomingModel.modelName(),
                        incomingModel.modelType(),
                        incomingModel.modelYear(),
                        brandId
                );
                CreateCarModelResponse createdModel = carModelService.createCarModel(modelRequest);
                modelId = createdModel.id();

                if (request.car() == null) {
                    throw new MultiFieldException("Invalid car",
                            List.of(new FieldErrorResponse("car", "Car object is required when creating a new carModel")));
                }
            } else {
                throw new MultiFieldException("Invalid car model",
                        List.of(new FieldErrorResponse("car_model", "Either carModelID or carModel must be provided when creating a car")));
            }

            CreateCarCheckInRequest incomingCar = request.car();
            CreateCarRequest carRequest = new CreateCarRequest(
                    incomingCar.VIN(),
                    incomingCar.licensePlate(),
                    modelId
            );
            CreateCarResponse createdCar = carService.createCar(carRequest);

            car = entityFinder.findByIdOrThrow(carRepository, createdCar.id(), "car", "Car not found after creation");
        }

        List<Integer> combinedToolIds = new ArrayList<>();
        if (request.toolsIds() != null) {
            for (Integer toolId : request.toolsIds()) {
                Tool found = entityFinder.findByIdOrThrow(toolRepository, toolId, "tool_id", "Tool not found");
                combinedToolIds.add(found.getId());
            }
        }
        if (request.newTools() != null) {
            for (CreateToolRequest toolReq : request.newTools()) {
                Tool newTool = new Tool(toolReq.name());
                Tool savedTool = toolRepository.save(newTool);
                combinedToolIds.add(savedTool.getId());
            }
        }

        Record newRecord = new Record(
                request.reason(),
                car,
                clientInfo
        );
        Record savedRecord = recordRepository.save(newRecord);

        LocalDateTime entryTime = LocalDateTime.now();

        GasLevel gasLevelEnum = parseGasLevel(request.gasLevel())
                .orElseThrow(() -> new MultiFieldException(
                        "Some error in fields",
                        List.of(new FieldErrorResponse("gas_level", "Invalid gas level"))
                ));

        CheckIn checkIn = new CheckIn();
        checkIn.setRecord(savedRecord);
        checkIn.setEntryTime(entryTime);
        checkIn.setMileage(request.recordState().mileage());
        checkIn.setGasLevel(gasLevelEnum);
        checkIn.setValuables(request.valuables());
        checkIn.setObservations(request.observations());


        List<Integer> combinedMechanicalIds = new ArrayList<>();
        if (request.exteriorConditionsIds() != null) {
            for (Integer condId : request.exteriorConditionsIds()) {
                ExteriorCondition ec = entityFinder.findByIdOrThrow(exteriorConditionRepository, condId, "exterior_condition_id", "Exterior condition not found");
                CheckInConsiderConditions cc = new CheckInConsiderConditions();
                cc.setCheckIn(checkIn);
                cc.setExteriorCondition(ec);
                checkIn.getMechanicalConditions().add(cc);
                combinedMechanicalIds.add(condId);
            }
        }
        if (request.interiorConditionsIds() != null) {
            for (Integer condId : request.interiorConditionsIds()) {
                InteriorCondition ic = entityFinder.findByIdOrThrow(interiorConditionRepository, condId, "interior_condition_id", "Interior condition not found");
                CheckInConsiderConditions cc = new CheckInConsiderConditions();
                cc.setCheckIn(checkIn);
                cc.setInteriorCondition(ic);
                checkIn.getMechanicalConditions().add(cc);
                combinedMechanicalIds.add(condId);
            }
        }
        if (request.electricalConditionsIds() != null) {
            for (Integer condId : request.electricalConditionsIds()) {
                ElectricalSystemCondition el = entityFinder.findByIdOrThrow(electricalSystemConditionRepository, condId, "electrical_condition_id", "Electrical condition not found");
                CheckInConsiderConditions cc = new CheckInConsiderConditions();
                cc.setCheckIn(checkIn);
                cc.setElectricalSystemCondition(el);
                checkIn.getMechanicalConditions().add(cc);
                combinedMechanicalIds.add(condId);
            }
        }

        if (!combinedToolIds.isEmpty()) {
            for (Integer tId : combinedToolIds) {
                Tool t = entityFinder.findByIdOrThrow(toolRepository, tId, "tool_id", "Tool not found");
                CheckInHaveTool th = new CheckInHaveTool();
                th.setCheckIn(checkIn);
                th.setTool(t);
                checkIn.getTools().add(th);
            }
        }

        CheckIn saved = checkInRepository.save(checkIn);

        return new CreateCheckInResponse(
            saved.getId(),
            saved.getEntryTime().toString(),
            saved.getMileage(),
            saved.getGasLevel().toString(),
            saved.getValuables(),
            clientInfo.getId(),
            car.getId(),
            combinedMechanicalIds,
            combinedToolIds
        );
    }


    @Transactional(readOnly = true)
    public GetCheckInFullResponse getCheckInById(Integer checkInId) {
        CheckIn ci = entityFinder.findByIdOrThrow(checkInRepository, checkInId, "check_in_id", "Check-in not found");

        List<MechanicalConditionInfo> mechanicalConditions = getMechanicalConditionInfos(ci);

        List<String> tools = new ArrayList<>();
        if (ci.getTools() != null) {
            for (CheckInHaveTool th : ci.getTools()) {
                if (th == null) continue;
                if (th.getTool() != null && th.getTool().getToolName() != null) {
                    tools.add(th.getTool().getToolName());
                }
            }
        }

        String entryTime = ci.getEntryTime() != null ? ci.getEntryTime().toString() : null;
        Integer mileage = ci.getMileage();
        String gasLevel = ci.getGasLevel() != null ? ci.getGasLevel().toString() : null;
        String valuables = ci.getValuables();

        return new GetCheckInFullResponse(
                ci.getRecord().getId(),
                ci.getId(),
                ci.getRecord().getClientInfo().getId(),
                (ci.getRecord().getClientInfo().getFirstName() + " " + ci.getRecord().getClientInfo().getLastName()).trim(),
                ci.getRecord().getClientInfo().getEmailAddress(),
                ci.getRecord().getCar().getCarModel().getBrand().getBrandName(),
                ci.getRecord().getCar().getCarModel().getModelName(),
                ci.getRecord().getCar().getCarModel().getModelType(),
                ci.getRecord().getCar().getCarModel().getModelYear(),
                ci.getRecord().getCar().getLicensePlate(),
                ci.getRecord().getReason(),
                ci.getObservations(),
                mechanicalConditions,
                tools,
                entryTime,
                mileage,
                gasLevel,
                valuables
        );
    }

    @NotNull
    private static List<MechanicalConditionInfo> getMechanicalConditionInfos(CheckIn ci) {
        List<MechanicalConditionInfo> mechanicalConditions = new ArrayList<>();

        if (ci.getMechanicalConditions() != null) {
            for (CheckInConsiderConditions cc : ci.getMechanicalConditions()) {
                if (cc == null) continue;

                if (cc.getExteriorCondition() != null) {
                    ExteriorCondition ec = cc.getExteriorCondition();
                    mechanicalConditions.add(new MechanicalConditionInfo(
                            ec.getId(),
                            "exterior",
                            ec.getPartName() == null ? "" : ec.getPartName(),
                            ec.getPartConditionState() == null ? "" : ec.getPartConditionState()
                    ));
                }

                if (cc.getInteriorCondition() != null) {
                    InteriorCondition ic = cc.getInteriorCondition();
                    mechanicalConditions.add(new MechanicalConditionInfo(
                            ic.getId(),
                            "interior",
                            ic.getPartName() == null ? "" : ic.getPartName(),
                            ic.getPartConditionState() == null ? "" : ic.getPartConditionState()
                    ));
                }

                if (cc.getElectricalSystemCondition() != null) {
                    ElectricalSystemCondition el = cc.getElectricalSystemCondition();
                    mechanicalConditions.add(new MechanicalConditionInfo(
                            el.getId(),
                            "electrical",
                            el.getPartName() == null ? "" : el.getPartName(),
                            el.getPartConditionState() == null ? "" : el.getPartConditionState()
                    ));
                }
            }
        }
        return mechanicalConditions;
    }


    private Optional<GasLevel> parseGasLevel(String value) {
        if (value == null || value.isBlank()) return Optional.empty();
        String normalized = value.trim();

        for (GasLevel g : GasLevel.values()) {
            if (g.name().equalsIgnoreCase(normalized)) return Optional.of(g);
        }
        for (GasLevel g : GasLevel.values()) {
            if (g.toString().equalsIgnoreCase(normalized)) return Optional.of(g);
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public List<GetCheckInBasicResponse> getPendingCheckIns() {
        List<CheckIn> pending = checkInRepository.findCheckInsNotLinkedToWorkOrder();
        List<GetCheckInBasicResponse> results = new ArrayList<>();
        for (CheckIn ci : pending) {
            List<MechanicalCondition> conditions = getMechanicalConditionInfos(ci)
                    .stream()
                    .map(info -> new MechanicalCondition(
                            info.partName(),
                            info.partState()
                    ))
                    .toList();
            GetCheckInBasicResponse basic = new GetCheckInBasicResponse(
                    ci.getId(),
                    (ci.getRecord().getClientInfo().getFirstName() + " " + ci.getRecord().getClientInfo().getLastName()).trim(),
                    ci.getRecord().getClientInfo().getEmailAddress(),
                    ci.getRecord().getCar().getCarModel().getBrand().getBrandName(),
                    ci.getRecord().getCar().getCarModel().getModelName(),
                    ci.getRecord().getCar().getCarModel().getModelType(),
                    ci.getRecord().getCar().getCarModel().getModelYear(),
                    ci.getRecord().getCar().getLicensePlate(),
                    ci.getRecord().getReason(),
                    ci.getObservations(),
                    conditions,
                    ci.getEntryTime() != null ? ci.getEntryTime().toString() : null
            );
            results.add(basic);
        }
        return results;
    }
}
