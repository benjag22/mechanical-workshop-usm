package com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "electrical_system_condition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectricalSystemCondition  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "part_name", nullable = false, length = 64)
    private String partName;

    @Size(max = 128)
    @Column(name = "part_condition_state", length = 128)
    private String partConditionState;
}
