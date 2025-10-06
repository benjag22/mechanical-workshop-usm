package com.mechanical_workshop_usm.car_module.car;

import com.mechanical_workshop_usm.car_module.car_model.CarModel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private int id;

    @Column(name = "VIN", unique = true, nullable = false)
    private String VIN;

    @Column(name = "license_plate", unique = true, nullable = false)
    private String licensePlate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private CarModel carModel;

    public Car(String VIN, String licensePlate, CarModel carModel) {
        this.VIN = VIN;
        this.licensePlate = licensePlate;
        this.carModel = carModel;
    }
}
