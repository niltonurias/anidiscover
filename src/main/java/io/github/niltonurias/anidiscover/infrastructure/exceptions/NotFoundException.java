package io.github.niltonurias.anidiscover.infrastructure.exceptions;

import io.github.niltonurias.anidiscover.enums.NotFoundEnum;

public class NotFoundException extends RuntimeException {
    private final NotFoundEnum code;

    public NotFoundException(NotFoundEnum code) {
        this.code = code;
    }

    public NotFoundEnum getCode() {
        return this.code;
    }
}
