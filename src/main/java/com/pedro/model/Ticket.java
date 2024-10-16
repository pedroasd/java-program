package com.pedro.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tickets")
public class Ticket {

    public enum Category {STANDARD, PREMIUM, BAR}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private int place;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Transient
    private Long userId;

    @Transient
    private Long eventId;

    public Ticket() {
    }

    public Ticket(Long id, User user, Event event, int place, Category category) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
    }

    public Ticket(User user, Event event, int place, Category category) {
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;
        return place == ticket.place && Objects.equals(id, ticket.id) && Objects.equals(user, ticket.user) && Objects.equals(event, ticket.event) && category == ticket.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, event, place, category);
    }
}
