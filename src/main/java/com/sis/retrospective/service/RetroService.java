package com.sis.retrospective.service;

import com.sis.retrospective.model.FeedbackRecord;
import com.sis.retrospective.model.RetroRecord;
import com.sis.retrospective.repository.RetroRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetroService {

    private final RetroRepository retroRepository;

    public RetroService(RetroRepository retroRepository) {
        this.retroRepository = retroRepository;
    }

    public List<RetroRecord> getAllRetrospectives() {

        return retroRepository.findAll()
                .stream()
                .map(r -> new RetroRecord(r.getName(),
                        r.getSummary(),
                        r.getDate(),
                        r.getParticipants(),
                        Optional.ofNullable(r.getFeedback()).orElse(Collections.emptyList()).stream()
                        .map(f -> new FeedbackRecord(f.getName(), f.getBody(), f.getFeedbackType()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
