package com.pedro.service;

import com.pedro.model.Ticket;
import com.pedro.model.impl.EventImpl;
import com.pedro.model.impl.UserImpl;
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
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Test
    public void create(){
        var createdTicket = ticketService.bookTicket(1L, 1L, 1, Ticket.Category.STANDARD);
        assertTrue(createdTicket.getId() > 0);
    }

    @Test
    public void delete(){
        var createdTicket = ticketService.bookTicket(1L, 1L, 1, Ticket.Category.STANDARD);
        var isEventDeleted = ticketService.cancelTicket(createdTicket.getId());
        assertTrue(isEventDeleted);
    }

    @Test
    public void list(){
        var u1 = userService.createUser(new UserImpl("Jose", "jose@email.com"));
        var u2 = userService.createUser(new UserImpl("Martina", "martina@email.com"));

        var e1 = eventService.createEvent(new EventImpl("Program", new Date("2024/08/17 12:00:00")));
        var e2 = eventService.createEvent(new EventImpl("Community", new Date("2024/09/18 12:00:00")));

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
