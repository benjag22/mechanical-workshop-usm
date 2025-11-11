package com.mechanical_workshop_usm.client_info_module;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client_info")
public class ClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "cellphone_number", nullable = false)
    private String cellphoneNumber;

    public ClientInfo() {}

    public ClientInfo(String firstName, String lastName, String emailAddress, String address, String cellphoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.cellphoneNumber = cellphoneNumber;

    }
}
