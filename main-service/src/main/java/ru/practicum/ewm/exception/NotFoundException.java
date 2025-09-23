package ru.practicum.ewm.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Long eventId, Long userId) {
    }

    public NotFoundException(String s, Long userId) {
    }
}
