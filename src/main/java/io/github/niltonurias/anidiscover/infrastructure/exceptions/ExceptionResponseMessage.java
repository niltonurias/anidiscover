package io.github.niltonurias.anidiscover.infrastructure.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponseMessage {
    private Long status;
    private String message;
    private String detail;

    public ExceptionResponseMessage(String message) {
        this.message = message;
    }
}
