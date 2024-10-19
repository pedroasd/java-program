package com.pedro.integration;

import com.pedro.facade.BookingFacade;
import com.pedro.jms.JmsConsumer;
import com.pedro.jms.TicketMessage;
import com.pedro.model.Event;
import com.pedro.model.Ticket;
import com.pedro.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(AppTestConfig.class)
public class BookTicketAsycnTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsConsumer jmsConsumer;

    @Autowired
    private BookingFacade bookingFacade;

    @Test
    public void bookTicketWithEmbeddedActiveMQ() throws Exception {
        User user = bookingFacade.createUser(new User("pedro", "pedro@mail.com"));
        bookingFacade.refillAccount(user.getId(), 1000L);
        Event event = bookingFacade.createEvent(new Event("event1", new Date(), 10L));

        mockMvc.perform(post("/book-ticket-async?userId="+user.getId()+"&eventId="+event.getId()+"&category=PREMIUM&place=1"))
                .andExpect(status().isAccepted());

        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 10, 1);
        assertEquals(1, tickets.size());
        var ticket = tickets.get(0);
        assertEquals(user.getId(), ticket.getUser().getId());
        assertEquals(event.getId(), ticket.getEvent().getId());
        assertEquals(1, ticket.getPlace());
    }

    @Test
    public void bookTicketWithJmsMock() {
        User user = bookingFacade.createUser(new User("juan", "juan@mail.com"));
        bookingFacade.refillAccount(user.getId(), 1000L);
        Event event = bookingFacade.createEvent(new Event("event2", new Date(), 10L));

        JmsTemplate mockJmsTemplate = Mockito.mock(JmsTemplate.class);
        TicketMessage message =  new TicketMessage(user.getId(), event.getId(), Ticket.Category.PREMIUM.name(), 10);

        Mockito.when(mockJmsTemplate.receiveAndConvert("bookingQueue")).thenReturn(message);

        jmsConsumer.receiveMessage(message);

        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 10, 1);
        assertEquals(1, tickets.size());
        var ticket = tickets.get(0);
        assertEquals(user.getId(), ticket.getUser().getId());
        assertEquals(event.getId(), ticket.getEvent().getId());
        assertEquals(10, ticket.getPlace());
    }
}
