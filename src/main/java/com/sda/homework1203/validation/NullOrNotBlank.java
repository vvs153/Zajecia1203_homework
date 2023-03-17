package com.sda.homework1203.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
public @interface NullOrNotBlank {
    String message() default "Can be null but can't be blank!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}
