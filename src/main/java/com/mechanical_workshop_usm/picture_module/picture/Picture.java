package com.mechanical_workshop_usm.picture_module.picture;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "picture")
@NoArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "alt", length = 255, nullable = false)
    private String alt;

    @Column(name = "path", length = 512, nullable = false)
    private String path;

    public Picture(String alt, String path) {
        this.alt = alt;
        this.path = path;
    }
}