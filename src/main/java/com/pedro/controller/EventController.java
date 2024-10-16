package com.pedro.controller;

import com.pedro.model.Event;
import com.pedro.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    // Display the form to create a new event
    @GetMapping("/events/new")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "createEvent";
    }

    // Handle form submission to create a new event
    @PostMapping("/events")
    public String createEvent(Event event) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(formatter.parse(event.getEventDate()));
        bookingFacade.createEvent(event);
        return "redirect:/events/new?success";
    }

    // Display the form to search for events by title
    @GetMapping("/events/search")
    public String showSearchEventsForm() {
        return "searchEvents";
    }

    // Handle the search and display the results
    @GetMapping("/events")
    public String getEventsByTitle(@RequestParam(value = "title", required = false) String title, Model model) {
        if (title != null && !title.isEmpty()) {
            List<Event> events = bookingFacade.getEventsByTitle(title, 100,1);
            model.addAttribute("events", events);
            model.addAttribute("searchTitle", title);
        } else {
            model.addAttribute("events", null);
            model.addAttribute("searchTitle", "");
        }
        return "searchEvents";
    }
}

