package ru.practicum.ewm.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.exception.dto.ErrorResponse;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(ApiError.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiError ex, HttpServletRequest request) {
        HttpStatus status = switch (ex) {
            case NotFoundException ignored -> HttpStatus.NOT_FOUND;
            case BadRequestException ignored -> HttpStatus.BAD_REQUEST;
            case ConflictException ignored -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        log.warn("{}: {}", status, ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.name(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, status);
    }
}