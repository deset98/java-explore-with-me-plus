package ru.practicum.ewm.exception;

import org.springframework.http.HttpStatus;

public class ConditionNotMetException extends RuntimeException {

    public ConditionNotMetException(String message) {
        super(message);
    }

    public ConditionNotMetException(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }

    public ConditionNotMetException(HttpStatus httpStatus, String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}