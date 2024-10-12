package com.pedro.service;

import com.pedro.dao.EventDAO;
import com.pedro.model.Event;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private final Log log = LogFactory.getLog(EventService.class);

    private EventDAO eventDAO;

    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    /**
     * Gets event by its id.
     * @return Event.
     */
    public Event getEventById(long id) {
        return eventDAO.findById(id).orElse(null);
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
        return eventDAO.findAllByTitleIgnoreCaseContains(title, PageRequest.of(pageNum - 1, pageSize)).getContent();
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
        return eventDAO.findAllByDate(day, PageRequest.of(pageNum - 1, pageSize)).getContent();
    }

    /**
     * Creates new event. Event id should be auto-generated.
     *
     * @param event Event data.
     * @return Created Event object.
     */
    public Event createEvent(Event event) {
        var createdEvent = eventDAO.save(event);
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
        var updatedEvent = eventDAO.save(event);
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
        return eventDAO.findById(eventId).map(e -> {
            eventDAO.deleteById(eventId);
            log.info("Event id: " + eventId + " was deleted.");
            return true;
        }).orElse(false);
    }
}
