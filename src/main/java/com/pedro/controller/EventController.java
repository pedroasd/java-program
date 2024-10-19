package com.pedro.controller;

import com.pedro.model.Event;
import com.pedro.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private BookingFacade bookingFacade;

    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(Event event) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(formatter.parse(event.getEventDate()));
        return ResponseEntity.ok(bookingFacade.createEvent(event));
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEventsByTitle(@RequestParam(value = "title") String title) {
        return ResponseEntity.ok(bookingFacade.getEventsByTitle(title, 100,1));
    }
}

