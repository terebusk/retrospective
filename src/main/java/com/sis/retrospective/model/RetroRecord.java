package com.sis.retrospective.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record RetroRecord(@NotBlank
                          @Size(min = 5, message = "Retrospective name needs to be at least 5 characters long")
                          String name,
                          @NotBlank
                          String summary,
                          @Past
                          @JsonFormat(pattern = "yyyy/MM/dd")
                          LocalDate date,
                          @NotNull
                          @NotEmpty(message = "List of participants cannot be empty")
                          List<String> participants,

                          List<FeedbackRecord> feedback) {}
