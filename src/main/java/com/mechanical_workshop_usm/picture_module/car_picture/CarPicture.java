package com.mechanical_workshop_usm.picture_module.car_picture;

import com.mechanical_workshop_usm.work_order_module.WorkOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car_picture")
public class CarPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "work_order_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_car_picture_work_order"))
    private WorkOrder workOrder;

    @Column(name = "path", length = 512, nullable = false)
    private String path;

    public CarPicture(WorkOrder workOrder, String path) {
        this.workOrder = workOrder;
        this.path = path;
    }
}