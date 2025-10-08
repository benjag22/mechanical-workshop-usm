package com.mechanical_workshop_usm.check_in_has_tools_module;

import com.mechanical_workshop_usm.record_module.record_state.persistence.entity.CheckIn;
import com.mechanical_workshop_usm.tool_module.Tool;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "entry_state_has_tool")
@EqualsAndHashCode(exclude = {"checkIn", "tool"})
@IdClass(CheckInHaveToolPk.class)
public class CheckInHaveTool {

    @Id
    @ManyToOne
    @JoinColumn(name = "entry_state_id")
    private CheckIn checkIn;

    @Id
    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
}
