package com.pedro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.facade.BookingFacade;
import com.pedro.model.Event;
import com.pedro.model.Ticket;
import com.pedro.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingFacade bookingService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetUsersByName() throws Exception {
        User user1 = new User(1L, "Pedro Perez", "pedro@mail.com");
        User user2 = new User(2L, "Juan Perez", "juan@mail.com");
        List<User> users = Arrays.asList(user1, user2);

        var searchName = "Perez";
        when(bookingService.getUsersByName(eq(searchName),anyInt(),anyInt())).thenReturn(users);

        mockMvc.perform(get("/users?name=Perez"))
                .andExpect(status().isOk())
                .andExpect(view().name("searchUsers"))
                .andExpect(model().attribute("users", users))
                .andExpect(model().attribute("searchName", searchName));
    }

    @Test
    public void testBookTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setEventId(1L);
        ticket.setCategory(Ticket.Category.PREMIUM);
        ticket.setPlace(1);

        when(bookingService.bookTicket(anyLong(), anyLong(), anyInt(), any())).thenReturn(ticket);

        mockMvc.perform(post("/bookTicket?userId=1&eventId=1&category=PREMIUM&place=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookTicketResult"))
                .andExpect(model().attribute("message", "Ticket booked successfully!"));
    }

    @Test
    public void testGetBookedTickets() throws Exception {
        User user = new User(1L, "Pedro Perez", "pedro@mail.com");
        Event event1 = new Event(1L , "event1", new Date(), 10L);
        Event event2 = new Event(2L , "event2", new Date(), 20L);
        Ticket ticket1 = new Ticket(1L, user, event1, 1, Ticket.Category.PREMIUM);
        Ticket ticket2 = new Ticket(2L, user, event2, 1, Ticket.Category.STANDARD);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(bookingService.getUserById(eq(user.getId()))).thenReturn(user);
        when(bookingService.getBookedTickets(eq(user), anyInt(), anyInt())).thenReturn(tickets);

        mockMvc.perform(get("/tickets?userId=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("searchTickets"))
                .andExpect(model().attribute("userId", user.getId()))
                .andExpect(model().attribute("tickets", tickets))
                .andExpect(model().attribute("userName", user.getName()));
    }
}
