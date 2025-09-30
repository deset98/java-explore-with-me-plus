package ru.practicum.ewm.exception;

public class NotFoundException extends ApiError {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}