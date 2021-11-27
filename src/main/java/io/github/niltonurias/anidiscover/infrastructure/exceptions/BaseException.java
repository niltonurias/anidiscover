package io.github.niltonurias.anidiscover.infrastructure.exceptions;

import io.github.niltonurias.anidiscover.enums.ExceptionEnum;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ExceptionEnum code;

    public BaseException(ExceptionEnum code) {
        this.code = code;
    }

    public BaseException(Throwable throwable) {
        super(throwable);
        this.code = ExceptionEnum.INTERNAL_SERVER_ERROR;
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);
        this.code = ExceptionEnum.INTERNAL_SERVER_ERROR;
    }

    public BaseException(ExceptionEnum code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public BaseException(ExceptionEnum code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
}
