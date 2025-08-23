package com.mechanical_workshop_usm.car_module.persistence.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "car_model")
@EqualsAndHashCode(exclude = "cars")

public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "model_name", nullable = false)
    @Getter
    @Setter
    private String modelName;

    @Column(name = "model_type")
    @Getter
    @Setter
    private String modelType;

    @Column(name = "model_year")
    @Getter
    @Setter
    private int modelYear;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Car> cars = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private CarBrand brand;

    public CarModel() {}
    public CarModel(String modelName, String modelType, int modelYear) {}
}
