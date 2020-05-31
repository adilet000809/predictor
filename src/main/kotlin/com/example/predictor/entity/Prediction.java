package com.example.predictor.entity;

import javax.persistence.*;

@Entity
@Table(name = "prediction")
public class Prediction extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private Users user;

    @Column(name = "prediction")
    private String prediction;

    public Prediction() {
    }

    public Prediction(Event event, Users user, String prediction) {
        this.event = event;
        this.user = user;
        this.prediction = prediction;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

}
