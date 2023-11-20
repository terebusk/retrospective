package com.sis.retrospective.model;

import com.sis.retrospective.controller.util.IsValidFeedbackType;
import jakarta.validation.constraints.NotBlank;

public record FeedbackRecord(
        String name,
        @NotBlank
        String body,
        @IsValidFeedbackType String feedbackType) {
}
