package com.mechanical_workshop_usm.car_module.car;

import com.mechanical_workshop_usm.api.dto.FieldErrorResponse;
import com.mechanical_workshop_usm.api.exceptions.MultiFieldException;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarRequest;
import com.mechanical_workshop_usm.car_module.car.dto.CreateCarResponse;
import com.mechanical_workshop_usm.car_module.car.dto.GetCarFullResponse;
import com.mechanical_workshop_usm.car_module.car.dto.GetCarResponse;
import com.mechanical_workshop_usm.car_module.car_brand.CarBrand;
import com.mechanical_workshop_usm.car_module.car_model.CarModel;
import com.mechanical_workshop_usm.car_module.car_model.CarModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarValidator carValidator;
    private final CarModelRepository carModelRepository;

    public CarService(CarRepository carRepository, CarValidator carValidator, CarModelRepository carModelRepository) {
        this.carRepository = carRepository;
        this.carValidator = carValidator;
        this.carModelRepository = carModelRepository;
    }

    public CreateCarResponse createCar(CreateCarRequest request) {
        carValidator.validateOnCreate(request);

        Optional<CarModel> modelOpt = carModelRepository.findById((int)request.modelId());
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

    public GetCarFullResponse getCar(int carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new MultiFieldException(
                        "Car not found",
                        List.of(new FieldErrorResponse("car_id", "No car found for the provided ID"))
                ));

        CarModel carModel = car.getCarModel();
        CarBrand brand = carModel.getBrand();

        return new GetCarFullResponse(
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
}