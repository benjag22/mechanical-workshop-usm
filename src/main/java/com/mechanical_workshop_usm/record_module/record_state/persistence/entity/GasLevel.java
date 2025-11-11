package com.mechanical_workshop_usm.record_module.record_state.persistence.entity;


import lombok.Getter;

@Getter
public enum GasLevel {
    FULL("full"),
    THREE_QUARTERS("3/4"),
    HALF("half"),
    ONE_QUARTER("1/4"),
    LOW("low");

    private final String label;

    GasLevel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
