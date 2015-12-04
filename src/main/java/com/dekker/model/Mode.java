package com.dekker.model;

public enum Mode {
    STEP_BY_STEP("Пошагово"),
    REAL_TIME("В реальном времени");

    private final String value;

    Mode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
