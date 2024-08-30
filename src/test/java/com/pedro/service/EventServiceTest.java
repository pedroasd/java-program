package com.pedro.service;

import com.pedro.model.Event;
import com.pedro.model.impl.EventImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/context-config.xml")
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Test
    public void create(){
        Event event = new EventImpl("Java program", new Date("2024/08/17 12:00:00"));
        var createdEvent = eventService.createEvent(event);
        event.setId(createdEvent.getId());
        assertTrue(createdEvent.getId() > 0);
        assertEquals(event, createdEvent);
        assertEquals(createdEvent, eventService.getEventById(createdEvent.getId()));
    }

    @Test
    public void update(){
        var date = new Date("2024/08/17 12:00:00");
        Event event = new EventImpl("Java program", date);
        var createdEvent = eventService.createEvent(event);
        var updatedEvent = eventService.updateEvent(new EventImpl(createdEvent.getId(), "Java community", date));
        var storedEvent = eventService.getEventById(createdEvent.getId());
        assertEquals(updatedEvent, storedEvent);
    }

    @Test
    public void delete(){
        Event event = new EventImpl("Java program", new Date("2024/08/17 12:00:00"));
        var createdEvent = eventService.createEvent(event);
        var storedEvent = eventService.getEventById(createdEvent.getId());
        assertEquals(createdEvent, storedEvent);

        var isEventDeleted = eventService.deleteEvent(createdEvent.getId());
        assertTrue(isEventDeleted);
        assertNull(eventService.getEventById(createdEvent.getId()));
    }

    @Test
    public void list(){
        var e1 = eventService.createEvent(new EventImpl("Java program", new Date("2024/08/17 12:00:00")));
        var e2 = eventService.createEvent(new EventImpl("Java community", new Date("2024/09/18 12:00:00")));
        var e3 = eventService.createEvent(new EventImpl("Functional Java community", new Date("2024/09/17 12:00:00")));
        var e4 = eventService.createEvent(new EventImpl("Python community", new Date("2024/09/17 12:00:00")));

        var javaEvents = eventService.getEventsByTitle("java", 5, 1);
        assertTrue(javaEvents.containsAll(List.of(e1, e2, e3)));

        var eventsByDay = eventService.getEventsForDay(new Date("2024/09/17 12:00:00"), 5, 1);
        assertTrue(eventsByDay.containsAll(List.of(e3, e4)));
    }
}
