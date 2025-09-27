package ru.practicum.ewm.exception;

public abstract class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}