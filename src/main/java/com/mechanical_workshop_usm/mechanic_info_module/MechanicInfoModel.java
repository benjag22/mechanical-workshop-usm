package com.mechanical_workshop_usm.mechanic_info_module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mechanic_info")
public class MechanicInfoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    @Getter
    @Setter
    private String firstName;

    @Column(name = "last_name")
    @Getter
    @Setter
    private String lastName;

    @Column(name = "registration_number", nullable = false)
    @Getter
    @Setter
    private String registrationNumber;

    public MechanicInfoModel() {}

    public MechanicInfoModel(String firstName, String lastName, String registrationNumber) {}
}
