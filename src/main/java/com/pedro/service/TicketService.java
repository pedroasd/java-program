package com.pedro.service;

import com.pedro.dao.TicketDAO;
import com.pedro.model.Event;
import com.pedro.model.Ticket;
import com.pedro.model.User;
import com.pedro.model.impl.TicketImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final Log log = LogFactory.getLog(TicketService.class);

    @Autowired
    private TicketDAO ticketDAO;

    /**
     * Book ticket for a specified event on behalf of specified user.
     *
     * @param userId   User Id.
     * @param eventId  Event Id.
     * @param place    Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws java.lang.IllegalStateException if this place has already been booked.
     */
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        var ticket = ticketDAO.store(new TicketImpl(eventId, userId, category, place), Ticket::setId);
        log.info("Ticket booked for user: " + userId + " with id: " + ticket.getId());
        return ticket;
    }

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param user     User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketDAO.getPageBy(t -> t.getUserId() == user.getId(), pageSize, pageNum);
    }

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param event    Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketDAO.getPageBy(t -> t.getEventId() == event.getId(), pageSize, pageNum);
    }

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    public boolean cancelTicket(long ticketId) {
        var isCanceled = ticketDAO.delete(ticketId);
        if (isCanceled) log.info("Ticket id: " + ticketId + " was canceled.");
        return isCanceled;
    }
}
