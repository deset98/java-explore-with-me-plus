package ru.practicum.ewm.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}