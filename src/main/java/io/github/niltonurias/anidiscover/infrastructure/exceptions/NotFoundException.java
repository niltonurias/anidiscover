package io.github.niltonurias.anidiscover.infrastructure.exceptions;

import io.github.niltonurias.anidiscover.enums.ExceptionEnum;

public class NotFoundException extends BaseException {
    public NotFoundException(ExceptionEnum code) {
        super(code);
    }
}
