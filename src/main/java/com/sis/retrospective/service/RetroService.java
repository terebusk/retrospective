package com.sis.retrospective.service;

import com.sis.retrospective.entity.FeedbackEntity;
import com.sis.retrospective.entity.RetroEntity;
import com.sis.retrospective.model.FeedbackRecord;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.model.exception.ResourceNotFoundException;
import com.sis.retrospective.repository.RetroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetroService {

    private final RetroRepository retroRepository;

    public RetroService(RetroRepository retroRepository) {
        this.retroRepository = retroRepository;
    }

    public Page<RetroRecord> getAllRetrospectives(Pageable pageable) {
        return retroRepository.findAll(pageable)
                .map(r -> new RetroRecord(r.getName(),
                        r.getSummary(),
                        r.getDate(),
                        r.getParticipants(),
                        Optional.ofNullable(r.getFeedback()).orElse(Collections.emptyList()).stream()
                                .map(f -> new FeedbackRecord(f.getName(), f.getBody(), f.getFeedbackType()))
                                .collect(Collectors.toList())));
    }

    public Page<RetroRecord> getRetrospectivesByDate(LocalDate date, Pageable pageable) {
        return retroRepository.findByDate(date, pageable).map(r -> new RetroRecord(r.getName(),
                r.getSummary(),
                r.getDate(),
                r.getParticipants(),
                Optional.ofNullable(r.getFeedback()).orElse(Collections.emptyList()).stream()
                        .map(f -> new FeedbackRecord(f.getName(), f.getBody(), f.getFeedbackType()))
                        .collect(Collectors.toList())));
    }

    public void createRetrospective(RetroRecord retroRecord) {
        if (retroRecord.feedback() != null && !retroRecord.feedback().isEmpty()) {
            throw new IllegalArgumentException("Feedback should be empty when creating new Retrospective");
        }
        RetroEntity retroEntity = new RetroEntity();
        retroEntity.setName(retroRecord.name());
        retroEntity.setSummary(retroRecord.summary());
        retroEntity.setDate(retroRecord.date());
        retroEntity.setParticipants(retroRecord.participants());
        retroEntity.setFeedback(new ArrayList<>());

        retroRepository.save(retroEntity);
    }

    public Long createFeedback(String retrospectiveName, FeedbackRecord feedbackRecord) {
        return retroRepository.findById(retrospectiveName).map(r -> {
            r.getFeedback().addLast(new FeedbackEntity(feedbackRecord.name(), feedbackRecord.body(), feedbackRecord.feedbackType()));
            RetroEntity retroEntity = retroRepository.save(r);
            return retroEntity.getFeedback().getLast().getId();
        }).orElseThrow(() -> new ResourceNotFoundException("Retrospective with unique name: " + retrospectiveName + " doesn't exist."));
    }


}
