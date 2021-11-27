package io.github.niltonurias.anidiscover.infrastructure.handlers;

import io.github.niltonurias.anidiscover.enums.ExceptionEnum;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.BaseException;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ConflictException;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ExceptionResponseMessage;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ResourceBundle;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponseMessage> notFoundExceptionHandle(NotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> conflictException(ConflictException conflictException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictException.getObject());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseMessage> exception(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ExceptionResponseMessage> handleExceptionInternal(RuntimeException ex, HttpStatus status, WebRequest request) {
        var message = new ExceptionResponseMessage();
        message.setStatus((long) status.value());

        if (ex instanceof BaseException baseException) {
            message.setMessage(getMessageFromExceptionEnum(baseException.getCode()));
        } else {
            message.setMessage(ex.getMessage());
        }

        return ResponseEntity.status(status).body(message);
    }

    private String getMessageFromExceptionEnum(ExceptionEnum exceptionEnum) {
        return ResourceBundle.getBundle("exception").getString(exceptionEnum.getCode().toString());
    }
}
