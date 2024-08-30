package com.pedro.model.impl;

import com.pedro.model.Event;

import java.util.Date;

public class EventImpl implements Event {

    private long id;
    private String tittle;
    private Date date;

    public EventImpl(String tittle, Date date) {
        this.tittle = tittle;
        this.date = date;
    }

    public EventImpl(long id, String tittle, Date date) {
        this(tittle, date);
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return tittle;
    }

    @Override
    public void setTitle(String title) {
        this.tittle = title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }
}
