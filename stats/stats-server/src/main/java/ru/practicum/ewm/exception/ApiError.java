package ru.practicum.ewm.exception;

public abstract class ApiError extends RuntimeException {

    public ApiError(String message) {
        super(message);
    }

    public ApiError(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}