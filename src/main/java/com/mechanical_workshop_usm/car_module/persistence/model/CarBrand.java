package com.mechanical_workshop_usm.car_module.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "car_brand")

public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "brand_name", nullable = false, unique = true)
    @Getter
    @Setter
    private String brandName;

    @OneToMany(mappedBy = "car_model", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<CarModel> carModels = new LinkedHashSet<>();

    public CarBrand() {}

    public CarBrand(String brandName) {}
}
