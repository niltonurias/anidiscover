package io.github.niltonurias.anidiscover.enums;

public enum SeasonEnum {
    FALL("Fall"),
    SUMMER("Summer"),
    WINTER("Winter"),
    SPRING("Spring");

    private final String description;

    SeasonEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
