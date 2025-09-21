package ru.practicum.ewm.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException {


    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}