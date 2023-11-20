package com.sis.retrospective.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class FeedbackEntity {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String body;
    private String feedbackType;

    public FeedbackEntity() {};

    public FeedbackEntity(String name, String body, String feedbackType) {
        this.name = name;
        this.body = body;
        this.feedbackType = feedbackType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }
}
