package com.mechanical_workshop_usm.car_module.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car_model")
@EqualsAndHashCode(exclude = "cars")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(name = "model_type")
    private String modelType;

    @Column(name = "model_year")
    private int modelYear;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Car> cars = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private CarBrand brand;
}
