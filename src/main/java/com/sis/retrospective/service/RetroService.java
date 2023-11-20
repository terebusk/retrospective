package com.sis.retrospective.service;

import com.sis.retrospective.model.FeedbackRecord;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.repository.RetroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
