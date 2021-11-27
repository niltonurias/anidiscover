package io.github.niltonurias.anidiscover.enums;

public enum ExceptionEnum {
    ANIME_NOT_FOUND(404001L),
    GENRE_NOT_FOUND(404002L),

    INTERNAL_SERVER_ERROR(500L);

    private final Long code;

    ExceptionEnum(final Long code) {
        this.code = code;
    }

    public Long getCode() {
        return this.code;
    }
}
