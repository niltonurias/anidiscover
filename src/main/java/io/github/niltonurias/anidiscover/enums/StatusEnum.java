package io.github.niltonurias.anidiscover.enums;

public enum StatusEnum {
    RELEASING("Releasing"),
    FINISHED("Finished"),
    NOT_YET_RELEASED("Not yet Released");

    private final String description;

    StatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
