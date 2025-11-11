package com.mechanical_workshop_usm.record_module.record_state.service.check_in;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car.Car;
import com.mechanical_workshop_usm.car_module.car.CarRepository;
import com.mechanical_workshop_usm.car_module.car.CarService;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarRequest;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarResponse;
import com.mechanical_workshop_usm.car_module.car_model.CarModelService;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelRequest;
import com.mechanical_workshop_usm.car_module.car_model.dto.CreateCarModelResponse;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrandService;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandRequest;
import com.mechanical_workshop_usm.car_module.car_brand.dto.CreateCarBrandResponse;
import com.mechanical_workshop_usm.client_info_module.ClientInfo;
import com.mechanical_workshop_usm.client_info_module.ClientInfoRepository;
import com.mechanical_workshop_usm.client_info_module.dtos.CreateClientResponse;
import com.mechanical_workshop_usm.client_info_module.ClientInfoService;
import com.mechanical_workshop_usm.record_module.record.Record;
import com.mechanical_workshop_usm.record_module.record.RecordRepository;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInRequest;
import com.mechanical_workshop_usm.record_module.record_state.dto.check_in.CreateCheckInResponse;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckOut;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.GasLevel;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.RecordState;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.CheckInRepository;
import com.mechanical_workshop_usm.record_module.record_state.service.RecordStateValidator;
import com.mechanical_workshop_usm.tool_module.Tool;
import com.mechanical_workshop_usm.tool_module.ToolRepository;
import com.mechanical_workshop_usm.tool_module.ToolService;
import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;
import com.mechanical_workshop_usm.util.EntityFinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckInService {
    private final CheckInRepository checkInRepository;
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

    public CheckInService(
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
            ToolService toolService) {
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
    }

    public CreateCheckInResponse createCheckIn(CreateCheckInRequest request) {
        checkInValidator.validateOnCreate(request);
        if (request.clientId() == null) {
            clientInfoService.validateOnCreate(request.client());
        }
        if (request.carId() == null) {
            carService.validate(request.car());
            carModelService.validate(request.carModel());
            carBrandService.validate(request.carBrand());
        }
        else {
            Optional<Record> lastRecordOpt = recordRepository.findFirstByCar_IdOrderByIdDesc(request.carId());
            if (lastRecordOpt.isPresent()) {
                Record lastRecord = lastRecordOpt.get();
                boolean hasCheckIn = false;
                boolean hasCheckOut = false;
                for (RecordState rs : lastRecord.getRecordStates()) {
                    if (rs instanceof CheckIn) hasCheckIn = true;
                    if (rs instanceof CheckOut) hasCheckOut = true;
                }
                if (hasCheckIn && !hasCheckOut) {
                    List<FieldErrorResponse> errors = List.of(
                            new FieldErrorResponse("car_id", "Car has an unfinished check-in (missing check-out)")
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
        if (request.clientId() != null) {
            clientInfo = entityFinder.findByIdOrThrow(clientInfoRepository, request.clientId(), "client_id", "Client not found");
        }
        else {
            CreateClientResponse createdClient = clientInfoService.createClient(request.client());
            clientInfo = entityFinder.findByIdOrThrow(clientInfoRepository, createdClient.id(), "client", "Client not found after creation");
        }

        Car car;
        if (request.carId() != null) {
            car = entityFinder.findByIdOrThrow(carRepository, request.carId(), "car_id", "Car not found");
        }
        else {
            CreateCarBrandRequest brandRequest = request.carBrand();
            CreateCarBrandResponse createdBrand = carBrandService.createBrand(brandRequest);

            CreateCarModelRequest incomingModel = request.carModel();
            CreateCarModelRequest modelRequest = new CreateCarModelRequest(
                    incomingModel.modelName(),
                    incomingModel.modelType(),
                    incomingModel.modelYear(),
                    createdBrand.id()
            );
            CreateCarModelResponse createdModel = carModelService.createCarModel(modelRequest);

            CreateCarRequest incomingCar = request.car();
            CreateCarRequest carRequest = new CreateCarRequest(
                    incomingCar.VIN(),
                    incomingCar.licensePlate(),
                    createdModel.id()
            );
            CreateCarResponse createdCar = carService.createCar(carRequest);

            car = entityFinder.findByIdOrThrow(carRepository, createdCar.id(), "car", "Car not found after creation");
        }

        List<Integer> combinedToolIds = new ArrayList<>();
        if (request.toolsIds() != null) {
            for (Integer toolId : request.toolsIds()) {
                Tool found = entityFinder.findByIdOrThrow(toolRepository, toolId, "tool_id", "Tool not found");
                combinedToolIds.add(found.getId());
                // Crear asosiasion despues
            }
        }
        if (request. newTools() != null) {
            for (CreateToolRequest toolReq : request.newTools()) {
                Tool newTool = new Tool(toolReq.toolName());
                Tool savedTool = toolRepository.save(newTool);
                combinedToolIds.add(savedTool.getId());
            }
        }

        Record newRecord = new Record(
                request.reason(),
                car,
                clientInfo,
                null
        );
        Record savedRecord = recordRepository.save(newRecord);

        LocalDate entryDate = LocalDate.parse(request.recordState().entryDate());
        LocalTime entryTime = LocalTime.parse(request.recordState().entryTime());

        GasLevel gasLevelEnum = parseGasLevel(request.gasLevel())
                .orElseThrow(() -> new MultiFieldException(
                        "Some error in fields",
                        List.of(new FieldErrorResponse("gas_level", "Invalid gas level"))
                ));

        CheckIn checkIn = new CheckIn(
                gasLevelEnum,
                request.valuables(),
                entryDate,
                entryTime,
                request.recordState().mileage(),
                savedRecord
        );

        CheckIn saved = checkInRepository.save(checkIn);


        return new CreateCheckInResponse(
                saved.getId(),
                saved.getEntryDate().toString(),
                saved.getEntryTime().toString(),
                saved.getMileage(),
                saved.getGasLevel().toString(),
                saved.getValuables(),
                clientInfo.getId(),
                car.getId(),
                request.mechanicalConditionsIds(),
                combinedToolIds
        );
    }


    @Transactional(readOnly = true)
    public List<CreateCheckInResponse> getAllCheckIns() {
        return checkInRepository.findAll().stream()
                .map(ci -> new CreateCheckInResponse(
                        ci.getId(),
                        ci.getEntryDate() != null ? ci.getEntryDate().toString() : null,
                        ci.getEntryTime() != null ? ci.getEntryTime().toString() : null,
                        ci.getMileage(),
                        ci.getGasLevel() != null ? ci.getGasLevel().toString() : null,
                        ci.getValuables(),
                        ci.getRecord() != null && ci.getRecord().getClientInfo() != null ? ci.getRecord().getClientInfo().getId() : 0,
                        ci.getRecord() != null && ci.getRecord().getCar() != null ? ci.getRecord().getCar().getId() : 0,
                        List.of(),
                        List.of()
                ))
                .toList();
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
}