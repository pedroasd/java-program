package com.pedro.dao;

import com.pedro.model.impl.EventImpl;
import jakarta.annotation.PostConstruct;
import com.pedro.model.Event;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class EventDAO extends DAO<Event> {

    private final Log log = LogFactory.getLog(EventDAO.class);

    @Value("${data.events}")
    private String dataFile;

    @PostConstruct
    public void init(){
        loadData(dataFile, Event::setId);
        log.info(data.size() + " events were loaded from " + dataFile);
    }

    @Override
    protected Event from(String fileLine) {
        var data = fileLine.split(",");
        return new EventImpl(data[0], new Date(data[1]));
    }
}
