package com.pedro.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Transient
    private String eventDate;

    @Column(name = "ticket_price")
    private Long ticketPrice;

    public Event() {}

    public Event(String title, Date date, Long ticketPrice) {
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
    }

    public Event(long id, String title, Date date, Long ticketPrice) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return id == event.id && Objects.equals(title, event.title) && Objects.equals(date, event.date) && Objects.equals(ticketPrice, event.ticketPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, ticketPrice);
    }
}
