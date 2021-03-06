package ru.kai.dekker.model.resource;

public enum  ResourceType {

    EMPTY("Пустой ресурс"),
    FILE("Ресурс файл"),
    BOARD("Ресурс-плата");

    private final String value;

    ResourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
