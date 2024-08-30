package com.pedro.integration;

import com.pedro.facade.BookingFacade;
import com.pedro.model.Event;
import com.pedro.model.Ticket;
import com.pedro.model.User;
import com.pedro.model.impl.EventImpl;
import com.pedro.model.impl.UserImpl;
import org.junit.jupiter.api.DisplayName;
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
public class BookingTest {

    @Autowired
    BookingFacade booking;

    @Test
    @DisplayName("Create a user, two events and two tickets for each event. Then cancel one.")
    public void testBooking1() {
        User user = booking.createUser(new UserImpl("Pedro", "pedro@email.com"));

        Event event1 = booking.createEvent(new EventImpl("Java program", new Date("2024/08/17 12:00:00")));
        Event event2 = booking.createEvent(new EventImpl("Java community", new Date("2024/08/17 12:00:00")));

        Ticket ticket1 = booking.bookTicket(user.getId(), event1.getId(), 1, Ticket.Category.STANDARD);
        Ticket ticket2 = booking.bookTicket(user.getId(), event2.getId(), 5, Ticket.Category.PREMIUM);

        var userTickets = booking.getBookedTickets(user, 5, 1);
        assertEquals(2, userTickets.size());
        assertTrue(userTickets.containsAll(List.of(ticket1,ticket2)));

        var eventTickets = booking.getBookedTickets(event1, 5, 1);
        assertEquals(1, eventTickets.size());
        assertTrue(eventTickets.contains(ticket1));

        var isTicketCanceled = booking.cancelTicket(ticket1.getId());
        assertTrue(isTicketCanceled);

        userTickets = booking.getBookedTickets(user, 5, 1);
        assertEquals(1, userTickets.size());
        assertTrue(userTickets.contains(ticket2));
    }

    @Test
    @DisplayName("Create two users with two events and two tickets for each event and user.")
    public void testBooking2() {
        User user1 = booking.createUser(new UserImpl("Pedro", "pedro@email.com"));
        User user2 = booking.createUser(new UserImpl("Juan", "juan@email.com"));

        Event event1 = booking.createEvent(new EventImpl("Java program", new Date("2024/08/17 12:00:00")));
        Event event2 = booking.createEvent(new EventImpl("Java community", new Date("2024/08/17 12:00:00")));

        Ticket ticket1 = booking.bookTicket(user1.getId(), event1.getId(), 1, Ticket.Category.STANDARD);
        Ticket ticket2 = booking.bookTicket(user1.getId(), event2.getId(), 5, Ticket.Category.PREMIUM);
        Ticket ticket3 = booking.bookTicket(user2.getId(), event1.getId(), 2, Ticket.Category.BAR);
        Ticket ticket4 = booking.bookTicket(user2.getId(), event2.getId(), 10, Ticket.Category.STANDARD);

        var user1Tickets = booking.getBookedTickets(user1, 5, 1);
        assertEquals(2, user1Tickets.size());
        assertTrue(user1Tickets.containsAll(List.of(ticket1,ticket2)));

        var user2Tickets = booking.getBookedTickets(user2, 5, 1);
        assertEquals(2, user2Tickets.size());
        assertTrue(user2Tickets.containsAll(List.of(ticket3,ticket4)));

        var event1Tickets = booking.getBookedTickets(event1, 5, 1);
        assertEquals(2, event1Tickets.size());
        assertTrue(event1Tickets.containsAll(List.of(ticket1, ticket3)));

        var event2Tickets = booking.getBookedTickets(event2, 5, 1);
        assertEquals(2, event2Tickets.size());
        assertTrue(event2Tickets.containsAll(List.of(ticket2, ticket4)));
    }
}
