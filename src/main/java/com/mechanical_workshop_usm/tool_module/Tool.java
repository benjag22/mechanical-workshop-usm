package com.mechanical_workshop_usm.tool_module;

import com.mechanical_workshop_usm.check_in_has_tools_module.CheckInHaveTool;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tool")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private int id;

    @Column(name = "tool_name", unique = true, nullable = false)
    private String toolName;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL)
    private final Set<CheckInHaveTool> tools = new LinkedHashSet<>();

    public Tool(String toolName){
        this.toolName = toolName;
    }
}
