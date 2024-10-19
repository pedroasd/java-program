package com.pedro.jms;

import java.io.Serializable;

public class TicketMessage implements Serializable {

    private Long userId;
    private Long eventId;
    private String category;
    private Integer place;

    public TicketMessage(){}

    public TicketMessage(Long userId, Long eventId, String category, Integer place) {
        this.userId = userId;
        this.eventId = eventId;
        this.category = category;
        this.place = place;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }
}

