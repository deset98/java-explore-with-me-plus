package ru.practicum.ewm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        return new ErrorResponse(
                "Object not found",
                ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException ex) {
        return new ErrorResponse(
                "Bad Request",
                ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return new ErrorResponse(
                "Не найден объект.",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ErrorResponse handleValidationException(final ForbiddenException e) {
        return new ErrorResponse(
                "Объект не доступен.",
                e.getMessage()
        );
    }

    private record ErrorResponse(String error, String description){}
}

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
//    public ErrorResponse handleValidationException(final ValidationException e) {
//        return new ErrorResponse(
//                "Ошибка валидации.",
//                e.getMessage()
//        );
//    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.FORBIDDEN) //403
//    public ErrorResponse handleValidationException(final ForbiddenException e) {
//        return new ErrorResponse(
//                "Объект не доступен.",
//                e.getMessage()
//        );
//    }


//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
//    public ErrorResponse handleValidationException(final BadRequestException e) {
//        return new ErrorResponse(
//                "Ошибка в запросе.",
//                e.getMessage()
//        );
//    }
