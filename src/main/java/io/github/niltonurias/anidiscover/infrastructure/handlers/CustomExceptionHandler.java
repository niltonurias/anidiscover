package io.github.niltonurias.anidiscover.infrastructure.handlers;

import io.github.niltonurias.anidiscover.enums.ExceptionEnum;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.BaseException;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ConflictException;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ErrorMessage;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorMessage notFoundExceptionHandle(NotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, NOT_FOUND, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage methodArgumentMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return handleExceptionInternal(ex, BAD_REQUEST, request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> conflictException(ConflictException conflictException) {
        return new ResponseEntity<>(conflictException.getObject(), CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorMessage exception(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, INTERNAL_SERVER_ERROR, request);
    }

    private ErrorMessage handleExceptionInternal(RuntimeException ex, HttpStatus status, WebRequest request) {
        var message = new ErrorMessage(status.value(), "", ZonedDateTime.now(), request.getDescription(false));
        var exceptionCode = ex instanceof BaseException ? ((BaseException) ex).getCode() : ExceptionEnum.INTERNAL_SERVER_ERROR;
        message.setMessage(getMessageFromExceptionEnum(exceptionCode));
        return message;
    }

    private String getMessageFromExceptionEnum(ExceptionEnum exceptionEnum) {
        return ResourceBundle.getBundle("exception").getString(exceptionEnum.getCode().toString());
    }
}
