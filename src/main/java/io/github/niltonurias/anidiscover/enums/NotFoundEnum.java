package io.github.niltonurias.anidiscover.enums;

public enum NotFoundEnum {
    ANIME_NOT_FOUND(404001L),
    GENRE_NOT_FOUND(404002L);

    private final Long code;

    NotFoundEnum(final Long code) {
        this.code = code;
    }

    public Long getCode() {
        return this.code;
    }
}
