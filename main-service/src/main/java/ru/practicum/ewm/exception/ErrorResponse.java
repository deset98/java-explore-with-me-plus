package ru.practicum.ewm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String error;
    private final String errorMessage;
}