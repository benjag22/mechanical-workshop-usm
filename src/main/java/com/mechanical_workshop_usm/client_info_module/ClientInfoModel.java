package com.mechanical_workshop_usm.client_info_module;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "client_info")
public class ClientInfoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name", nullable = false)
    @Getter
    @Setter
    private String firstName;

    @Column(name = "last_name")
    @Getter
    @Setter
    private String lastName;

    @Column(name = "email_address", nullable = false)
    @Getter
    @Setter
    private String emailAddress;

    @Column(name = "address", nullable = false)
    @Getter
    @Setter
    private String address;

    @Column(name = "cellphone_number", nullable = false)
    @Getter
    @Setter
    private String cellphoneNumber;

    public ClientInfoModel() {}

    public ClientInfoModel(String firstName, String lastName, String emailAddress, String address, String cellphoneNumber) {}
}
