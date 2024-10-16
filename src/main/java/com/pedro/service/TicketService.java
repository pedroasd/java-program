package com.pedro.service;

import com.pedro.dao.EventDAO;
import com.pedro.dao.TicketDAO;
import com.pedro.dao.UserDAO;
import com.pedro.model.Event;
import com.pedro.model.Ticket;
import com.pedro.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    private final Log log = LogFactory.getLog(TicketService.class);

    private TicketDAO ticketDAO;

    private EventDAO eventDAO;

    private UserDAO userDAO;

    public TicketService(TicketDAO ticketDAO, EventDAO eventDAO, UserDAO userDAO) {
        this.ticketDAO = ticketDAO;
        this.eventDAO = eventDAO;
        this.userDAO = userDAO;
    }

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
        var user = userDAO.findById(userId).orElse(null);
        var event = eventDAO.findById(eventId).orElse(null);
        var ticket = ticketDAO.save(new Ticket(user, event, place, category));
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
        return ticketDAO.findAllByUser(user, PageRequest.of(pageNum - 1, pageSize)).getContent();
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
        return ticketDAO.findAllByEvent(event, PageRequest.of(pageNum - 1, pageSize)).getContent();
    }

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    public boolean cancelTicket(long ticketId) {
        return ticketDAO.findById(ticketId).map(e -> {
            ticketDAO.deleteById(ticketId);
            log.info("Ticket id: " + ticketId + " was canceled.");
            return true;
        }).orElse(false);
    }

    @Transactional
    public void preloadTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticketDAO.save(ticket);
        }
    }
}
