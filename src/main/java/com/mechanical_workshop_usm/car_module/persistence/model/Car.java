package com.mechanical_workshop_usm.car_module.persistence.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "car")
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "VIN", unique = true, nullable = false)
    @Getter
    @Setter
    private String VIN;

    @Column(name = "license_plate", unique = true, nullable = false)
    @Getter
    @Setter
    private String licensePlate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn("car_mode_id")
    private CarModel carModel;

    public Car() {}

    public Car(String VIN, String licensePlate, CarModel carModel) {}

}
