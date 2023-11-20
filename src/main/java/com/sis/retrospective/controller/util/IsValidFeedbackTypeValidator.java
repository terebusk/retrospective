package com.sis.retrospective.controller.util;

import com.sis.retrospective.model.FeedbackType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class IsValidFeedbackTypeValidator implements ConstraintValidator<IsValidFeedbackType, String> {

    private List<String> valueList = null;

    @Override
    public void initialize(IsValidFeedbackType constraintAnnotation) {
        valueList = new ArrayList<>();

        for (@SuppressWarnings("rawtypes") Enum enumVal : FeedbackType.values()) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }

}
