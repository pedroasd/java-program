package com.pedro.jms;

import com.pedro.facade.BookingFacade;
import com.pedro.model.Ticket;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JmsConsumer {

    private final BookingFacade bookingService;

    public JmsConsumer(BookingFacade bookingService) {
        this.bookingService = bookingService;
    }

    @JmsListener(destination = "bookingQueue")
    @Transactional
    public void receiveMessage(TicketMessage message) {
        bookingService.bookTicket(
                message.getUserId(),
                message.getEventId(),
                message.getPlace(),
                Ticket.Category.valueOf(message.getCategory().toUpperCase()));
    }
}

