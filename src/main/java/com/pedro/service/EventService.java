package com.pedro.service;

import com.pedro.dao.EventDAO;
import com.pedro.model.Event;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private final Log log = LogFactory.getLog(EventService.class);

    @Autowired
    private EventDAO eventDAO;

    /**
     * Gets event by its id.
     * @return Event.
     */
    public Event getEventById(long id) {
        return eventDAO.get(id);
    }

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     * @param title Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventDAO.getPageBy(e -> e.getTitle().toLowerCase().contains(title.toLowerCase()), pageSize, pageNum);
    }

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     *
     * @param day      Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventDAO.getPageBy(e -> e.getDate().equals(day), pageSize, pageNum);
    }

    /**
     * Creates new event. Event id should be auto-generated.
     *
     * @param event Event data.
     * @return Created Event object.
     */
    public Event createEvent(Event event) {
        var createdEvent = eventDAO.store(event, Event::setId);
        log.info("Event: " + event.getTitle() + " was created with id: " + event.getId());
        return createdEvent;
    }

    /**
     * Updates event using given data.
     *
     * @param event Event data for update. Should have id set.
     * @return Updated Event object.
     */
    public Event updateEvent(Event event) {
        var updatedEvent = eventDAO.update(event.getId(), event);
        log.info("Event: " + updatedEvent.getTitle() + " was update with id: " + event.getId());
        return updatedEvent;
    }

    /**
     * Deletes event by its id.
     *
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    public boolean deleteEvent(long eventId) {
        var isDeleted = eventDAO.delete(eventId);
        if (isDeleted) log.info("Event id: " + eventId + " was deleted.");
        return isDeleted;
    }
}
