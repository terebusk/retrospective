package com.sis.retrospective.controller.util;

import com.sis.retrospective.model.FeedbackType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = IsValidFeedbackTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Documented
public @interface IsValidFeedbackType {

    String message() default "Provided feedbackType is not one of: positive, negative, idea, praise";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}