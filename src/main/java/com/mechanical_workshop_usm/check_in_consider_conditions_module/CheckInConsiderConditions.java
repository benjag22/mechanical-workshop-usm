package com.mechanical_workshop_usm.check_in_consider_conditions_module;

import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ElectricalSystemCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.ExteriorCondition;
import com.mechanical_workshop_usm.mechanical_condition_module.persistence.entity.InteriorCondition;
import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "entry_state_consider_condition")
@NoArgsConstructor
public class CheckInConsiderConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "entry_state_id")
    private CheckIn checkIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exterior_condition_id")
    private ExteriorCondition exteriorCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interior_condition_id")
    private InteriorCondition interiorCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electrical_system_condition_id")
    private ElectricalSystemCondition electricalSystemCondition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckInConsiderConditions)) return false;
        CheckInConsiderConditions that = (CheckInConsiderConditions) o;

        // if both persisted compare by id
        if (this.id != null && that.id != null) {
            return Objects.equals(this.id, that.id);
        }

        // otherwise build comparison key
        Integer thisCheckInId = (this.checkIn != null) ? this.checkIn.getId() : null;
        Integer thatCheckInId = (that.checkIn != null) ? that.checkIn.getId() : null;
        if (!Objects.equals(thisCheckInId, thatCheckInId)) return false;

        String thisType;
        Integer thisCondId;
        if (this.exteriorCondition != null) {
            thisType = "exterior";
            thisCondId = this.exteriorCondition.getId();
        } else if (this.interiorCondition != null) {
            thisType = "interior";
            thisCondId = this.interiorCondition.getId();
        } else if (this.electricalSystemCondition != null) {
            thisType = "electrical";
            thisCondId = this.electricalSystemCondition.getId();
        } else {
            thisType = null;
            thisCondId = null;
        }

        String thatType;
        Integer thatCondId;
        if (that.exteriorCondition != null) {
            thatType = "exterior";
            thatCondId = that.exteriorCondition.getId();
        } else if (that.interiorCondition != null) {
            thatType = "interior";
            thatCondId = that.interiorCondition.getId();
        } else if (that.electricalSystemCondition != null) {
            thatType = "electrical";
            thatCondId = that.electricalSystemCondition.getId();
        } else {
            thatType = null;
            thatCondId = null;
        }

        return Objects.equals(thisType, thatType) &&
                Objects.equals(thisCondId, thatCondId);
    }

    @Override
    public int hashCode() {
        // If persisted use id
        if (this.id != null) {
            return Objects.hash(this.id);
        }

        Integer checkInId = (this.checkIn != null) ? this.checkIn.getId() : null;
        String type;
        Integer condId;
        if (this.exteriorCondition != null) {
            type = "exterior";
            condId = this.exteriorCondition.getId();
        } else if (this.interiorCondition != null) {
            type = "interior";
            condId = this.interiorCondition.getId();
        } else if (this.electricalSystemCondition != null) {
            type = "electrical";
            condId = this.electricalSystemCondition.getId();
        } else {
            type = null;
            condId = null;
        }
        return Objects.hash(checkInId, type, condId);
    }
}