package com.sis.retrospective.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "retro")
public class RetroEntity {
    @Id
    private String name;
    private String summary;
    private LocalDate date;
    private List<String> participants;

    @OneToMany(cascade=CascadeType.ALL)
    private List<FeedbackEntity> feedback;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public List<FeedbackEntity> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<FeedbackEntity> feedback) {
        this.feedback = feedback;
    }
}
