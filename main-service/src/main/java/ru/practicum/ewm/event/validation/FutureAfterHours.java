package ru.practicum.ewm.event.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureAfterHoursValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureAfterHours {

    String message() default "Событие должно быть в будущем, минимум через {hours} часов";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int hours() default 2;
}