package com.mechanical_workshop_usm.record_module.persistence.model;


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

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}