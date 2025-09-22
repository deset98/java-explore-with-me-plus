package ru.practicum.ewm.event.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class FutureAfterHoursValidator implements ConstraintValidator<FutureAfterHours, LocalDateTime> {

    private int hours;

    @Override
    public void initialize(FutureAfterHours annotation) {
        this.hours = annotation.hours();
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return true;
        LocalDateTime nowPlusHours = LocalDateTime.now().plusHours(hours);
        return value.isAfter(nowPlusHours);
    }
}