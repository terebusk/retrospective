package com.sis.retrospective.controller;

import com.sis.retrospective.model.FeedbackRecord;
import com.sis.retrospective.service.FeedbackService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PutMapping("/feedbacks/{id}")
    public void updateFeedback(@PathVariable @NotBlank Long id, @RequestBody @Valid FeedbackRecord feedbackRecord) {
        log.info("Updating feedback.");
        feedbackService.updateFeedback(id, feedbackRecord);
    }
}
