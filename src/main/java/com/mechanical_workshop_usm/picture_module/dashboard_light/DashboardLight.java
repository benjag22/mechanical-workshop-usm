package com.mechanical_workshop_usm.picture_module.dashboard_light;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dashboard_light")
@NoArgsConstructor
public class DashboardLight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "alt", nullable = false)
    private String alt;

    @Column(name = "path", length = 512, nullable = false)
    private String path;

    public DashboardLight(String alt, String path) {
        this.alt = alt;
        this.path = path;
    }
}