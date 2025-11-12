package com.mechanical_workshop_usm.dashboard_light_module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "dashboard_light")
@NoArgsConstructor
public class DashboardLight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "alt", length = 255, nullable = false)
    private String alt;

    @Column(name = "path", length = 512, nullable = false)
    private String path;

    public DashboardLight(String alt, String path) {
        this.alt = alt;
        this.path = path;
    }
}