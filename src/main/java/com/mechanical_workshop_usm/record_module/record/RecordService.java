package com.mechanical_workshop_usm.record_module.record;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrand;
import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordRequest;
import com.mechanical_workshop_usm.record_module.record.dto.CreateRecordResponse;
import com.mechanical_workshop_usm.car_module.car.CarRepository;
import com.mechanical_workshop_usm.client_info_module.ClientInfoRepository;
import com.mechanical_workshop_usm.mechanic_info_module.MechanicInfoRepository;
import com.mechanical_workshop_usm.record_module.record.dto.GetRecordResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final CarRepository carRepository;
    private final ClientInfoRepository clientInfoRepository;
    private final MechanicInfoRepository mechanicInfoRepository;
    private final RecordValidator recordValidator;

    public RecordService(
            RecordRepository recordRepository,
            CarRepository carRepository,
            ClientInfoRepository clientInfoRepository,
            MechanicInfoRepository mechanicInfoRepository,
            RecordValidator recordValidator
    ) {
        this.recordRepository = recordRepository;
        this.carRepository = carRepository;
        this.clientInfoRepository = clientInfoRepository;
        this.mechanicInfoRepository = mechanicInfoRepository;
        this.recordValidator = recordValidator;
    }

    public CreateRecordResponse createRecord(CreateRecordRequest request) {
        recordValidator.validateOnCreate(request);

        var car = carRepository.findById(request.carId())
                .orElseThrow(() -> new MultiFieldException(
                        "Some error in fields",
                        List.of(new FieldErrorResponse("car_id", "Car not found"))

                ));

        var client = clientInfoRepository.findById(request.clientInfoId())
                .orElseThrow(() -> new MultiFieldException(
                        "Some error in fields",
                        List.of(new FieldErrorResponse("client_id", "Client not found"))

                ));
        var mechanic = mechanicInfoRepository.findById(request.mechanicInfoId())
                .orElseThrow(() -> new MultiFieldException(
                        "Some error in fields",
                        List.of(new FieldErrorResponse("mechanic_id", "Mechanic not found"))

                ));

        Record record = new Record(
                request.reason(),
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