package com.sis.retrospective.service;

import com.sis.retrospective.controller.FeedbackController;
import com.sis.retrospective.model.FeedbackRecord;
import com.sis.retrospective.model.exception.ResourceNotFoundException;
import com.sis.retrospective.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private static final Logger log = LoggerFactory.getLogger(FeedbackService.class);
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void updateFeedback(Long feedbackId, FeedbackRecord feedbackRecord) {
        feedbackRepository.findById(feedbackId).map(r -> {
            r.setBody(feedbackRecord.body());
            r.setFeedbackType(feedbackRecord.feedbackType());
            return feedbackRepository.save(r);
        }).orElseThrow(() -> new ResourceNotFoundException("Feedback with unique id: " + feedbackId + " doesn't exist."));
    }
}
