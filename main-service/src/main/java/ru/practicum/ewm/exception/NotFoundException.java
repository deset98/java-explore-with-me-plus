package ru.practicum.ewm.exception;

public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}