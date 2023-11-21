package com.sis.retrospective.controller.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IsValidDateValidator.class)
public @interface IsValidDate {

    String message() default "Provided Date is not of yyyy/MM/dd format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}