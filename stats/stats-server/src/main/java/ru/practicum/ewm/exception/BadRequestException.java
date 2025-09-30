package ru.practicum.ewm.exception;

public class BadRequestException extends ApiError {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Object... args) {
        super(message, args);
    }
}