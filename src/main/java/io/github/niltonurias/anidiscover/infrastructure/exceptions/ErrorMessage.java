package io.github.niltonurias.anidiscover.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private String message;
    private ZonedDateTime timestamp;
    private String description;
}
