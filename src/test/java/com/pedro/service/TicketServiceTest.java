package com.pedro.service;

import com.pedro.model.Ticket;
import com.pedro.model.Event;
import com.pedro.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Test
    public void create(){
        var user = userService.createUser(new User("Pedro", "pedro@email.com"));
        var event = eventService.createEvent(new Event("Program", new Date("2024/08/17 12:00:00"), 10L));
        var createdTicket = ticketService.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD);
        assertTrue(createdTicket.getId() > 0);
    }

    @Test
    public void delete(){
        var user = userService.createUser(new User("Jose", "jose@email.com"));
        var event = eventService.createEvent(new Event("Program", new Date("2024/08/17 12:00:00"), 10L));
        var createdTicket = ticketService.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD);
        var isEventDeleted = ticketService.cancelTicket(createdTicket.getId());
        assertTrue(isEventDeleted);
    }

    @Test
    public void list(){
        var u1 = userService.createUser(new User("Pedro", "pedro@email.com"));
        var u2 = userService.createUser(new User("Martina", "martina@email.com"));

        var e1 = eventService.createEvent(new Event("Program", new Date("2024/08/17 12:00:00"), 10L));
        var e2 = eventService.createEvent(new Event("Community", new Date("2024/09/18 12:00:00"), 20L));

        var t1 = ticketService.bookTicket(u1.getId(), e1.getId(), 15, Ticket.Category.STANDARD);
        var t2 = ticketService.bookTicket(u1.getId(), e2.getId(), 20, Ticket.Category.PREMIUM);
        var t3 = ticketService.bookTicket(u2.getId(), e1.getId(), 23, Ticket.Category.STANDARD);
        var t4 = ticketService.bookTicket(u2.getId(), e2.getId(), 18, Ticket.Category.BAR);

        var u1Tickets = ticketService.getBookedTickets(u1, 5, 1);
        assertTrue(u1Tickets.containsAll(List.of(t1, t2)));

        var u2Tickets = ticketService.getBookedTickets(u2, 5, 1);
        assertTrue(u2Tickets.containsAll(List.of(t3, t4)));

        var e1Tickets = ticketService.getBookedTickets(e1, 5, 1);
        assertTrue(e1Tickets.containsAll(List.of(t1, t3)));
    }
}
