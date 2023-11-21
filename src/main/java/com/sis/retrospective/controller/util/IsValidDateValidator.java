package com.sis.retrospective.controller.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeParseException;

public class IsValidDateValidator implements ConstraintValidator<IsValidDate, String> {
    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext context) {
        try {
            DateFormatting.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
