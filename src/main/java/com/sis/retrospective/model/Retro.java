package com.sis.retrospective.model;

import java.time.LocalDate;
import java.util.List;

public record Retro(String name,
                    String summary,
                    LocalDate date,
                    List<String> participants,
                    List<Feedback> feedback) {
}
