package com.mechanical_workshop_usm.car_module.car;
import java.util.stream.Collectors;
import com.mechanical_workshop_usm.record_module.record.Record;
import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car.dto.*;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrand;
import com.mechanical_workshop_usm.car_module.car_model.CarModel;
import com.mechanical_workshop_usm.car_module.car_model.CarModelRepository;
import com.mechanical_workshop_usm.record_module.record.RecordRepository;
import com.mechanical_workshop_usm.record_module.record_state.persistence.repository.CheckOutRepository;
import com.mechanical_workshop_usm.work_order_module.WorkOrderRepository;
import org.springframework.stereotype.Service;
import com.mechanical_workshop_usm.car_module.car.dto.GetCarResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarValidator carValidator;
    private final CarModelRepository carModelRepository;
    private final RecordRepository recordRepository;
    private final CheckOutRepository checkOutRepository;
    private final WorkOrderRepository workOrderRepository;

    public CarService(
        CarRepository carRepository, CarValidator carValidator, CarModelRepository carModelRepository,
        RecordRepository recordRepository, CheckOutRepository checkOutRepository,
        WorkOrderRepository workOrderRepository) {
        this.carRepository = carRepository;
        this.carValidator = carValidator;
        this.carModelRepository = carModelRepository;
        this.recordRepository = recordRepository;
        this.checkOutRepository = checkOutRepository;
        this.workOrderRepository = workOrderRepository;
    }

    public void validate(CreateCarCheckInRequest createCarCheckInRequest) {
        carValidator.validate(createCarCheckInRequest);
    }

    public CreateCarResponse createCar(CreateCarRequest request) {
        carValidator.validateOnCreate(request);

        Optional<CarModel> modelOpt = carModelRepository.findById(request.modelId());
        CarModel model = modelOpt.orElseThrow(() -> new MultiFieldException(
                "Some error in fields",
                List.of(new FieldErrorResponse("model_id", "Car model not found"))
        ));

        Car car = new Car(
                request.VIN(),
                request.licensePlate(),
                model
        );

        Car savedCar = carRepository.save(car);
        return new CreateCarResponse(
                savedCar.getId(),
                savedCar.getLicensePlate()
        );
    }

    public List<GetCarResponse> getAllCars() {
        return carRepository.findAll().stream()
                .map(car -> new GetCarResponse(
                        car.getId(),
                        car.getVIN(),
                        car.getLicensePlate(),
                        car.getCarModel().getId(),
                        car.getCarModel().getModelName()
                ))
                .toList();
    }
    public List<String> getAllPatents(){
        List<String> response = new ArrayList<>();
        carRepository.findAll().forEach(car -> response.add(car.getLicensePlate()));
        return response;
    }
    public GetCar getCarById(int carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new MultiFieldException(
                        "Car not found",
                        List.of(new FieldErrorResponse("car_id", "No car found for the provided ID"))
                ));

        CarModel carModel = car.getCarModel();
        CarBrand brand = carModel.getBrand();

        return new GetCar(
                car.getId(),
                car.getVIN(),
                car.getLicensePlate(),
                carModel.getId(),
                carModel.getModelName(),
                carModel.getModelType(),
                carModel.getModelYear(),
                brand.getId(),
                brand.getBrandName()
        );
    }
    public GetCar getCarByPatent(String patent) {
        Car car = carRepository.findByLicensePlate(patent)
            .orElseThrow(() -> new MultiFieldException(
                "Car not found",
                List.of(new FieldErrorResponse("car_id", "No car found for the provided ID"))
            ));

        CarModel carModel = car.getCarModel();
        CarBrand brand = carModel.getBrand();

        return new GetCar(
            car.getId(),
            car.getVIN(),
            car.getLicensePlate(),
            carModel.getId(),
            carModel.getModelName(),
            carModel.getModelType(),
            carModel.getModelYear(),
            brand.getId(),
            brand.getBrandName()
        );
    }
    public List<GetCarState> getCarStates() {
        List<Car> cars = carRepository.findAll();

        return cars.stream().map(car -> {
            List<Record> records = recordRepository.findAll().stream()
                .filter(r -> r.getCar().getId() == car.getId())
                .toList();

            boolean isAvailable = true;
            boolean atCheckIn = false;
            boolean atWorkOrder = false;

            for (Record record : records) {
                int recordId = record.getId();
                boolean hasCheckOut = checkOutRepository.existsByRecord_Id(recordId);
                boolean hasWorkOrder = workOrderRepository.existsByRecord_Id(recordId);

                if (!hasCheckOut) {
                    isAvailable = false;
                    if (hasWorkOrder) {
                        atWorkOrder = true;
                    } else {
                        atCheckIn = true;
                    }
                }
            }

            return new GetCarState(
                car.getId(),
                car.getVIN(),
                car.getLicensePlate(),
                car.getCarModel().getId(),
                car.getCarModel().getModelName(),
                isAvailable ? true : null,
                atCheckIn ? true : null,
                atWorkOrder ? true : null
            );
        }).toList();
    }
}