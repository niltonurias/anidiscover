package io.github.niltonurias.anidiscover.infrastructure.exceptions;

public class ConflictException extends RuntimeException {
    private final Object object;

    public ConflictException(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return this.object;
    }
}
