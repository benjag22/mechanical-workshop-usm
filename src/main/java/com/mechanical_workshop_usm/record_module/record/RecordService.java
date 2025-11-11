package com.mechanical_workshop_usm.record_module.record;

import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordRequest;
import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordResponse;
import com.mechanical_workshop_usm.car_module.car.CarRepository;
import com.mechanical_workshop_usm.client_info_module.ClientInfoRepository;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoRepository;
import com.mechanical_workshop_usm.record_module.record.dto.GetRecordResponse;
import com.mechanical_workshop_usm.tool_module.dto.CreateToolRequest;
import com.mechanical_workshop_usm.util.EntityFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final CarRepository carRepository;
    private final ClientInfoRepository clientInfoRepository;
    private final MechanicInfoRepository mechanicInfoRepository;
    private final RecordValidator recordValidator;
    private final EntityFinder entityFinder;

    public RecordService(
            RecordRepository recordRepository,
            CarRepository carRepository,
            ClientInfoRepository clientInfoRepository,
            MechanicInfoRepository mechanicInfoRepository,
            RecordValidator recordValidator,
            EntityFinder entityFinder
    ) {
        this.recordRepository = recordRepository;
        this.carRepository = carRepository;
        this.clientInfoRepository = clientInfoRepository;
        this.mechanicInfoRepository = mechanicInfoRepository;
        this.recordValidator = recordValidator;
        this.entityFinder = entityFinder;
    }

    public void validateOnCreate(CreateRecordRequest createRecordRequest) {
        recordValidator.validateOnCreate(createRecordRequest);
    }

    public CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) {
        recordValidator.validateOnCreate(createRecordRequest);

        var car = entityFinder.findByIdOrThrow(carRepository, createRecordRequest.carId(), "car_id", "Car not found");

        var client = entityFinder.findByIdOrThrow(clientInfoRepository, createRecordRequest.clientInfoId(), "client_id", "Client not found");

        var mechanic = entityFinder.findByIdOrThrow(mechanicInfoRepository, createRecordRequest.mechanicInfoId(), "mechanic_id", "Mechanic not found");

        Record record = new Record(
                createRecordRequest.reason(),
                car,
                client,
                mechanic
        );

        Record saved = recordRepository.save(record);
        return new CreateRecordResponse(
                saved.getId(),
                saved.getCar().getId(),
                saved.getClientInfo().getId(),
                saved.getMechanicInfo().getId()
        );
    }

    public List<GetRecordResponse> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(record -> new GetRecordResponse(
                        record.getReason(),
                        record.getCar().getLicensePlate(),
                        record.getClientInfo().getFirstName(),
                        record.getMechanicInfo().getFirstName()
                ))
                .toList();
    }
}