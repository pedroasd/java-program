package com.pedro.dao;

import com.pedro.model.impl.TicketImpl;
import jakarta.annotation.PostConstruct;
import com.pedro.model.Ticket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAO extends DAO<Ticket> {

    private final Log log = LogFactory.getLog(TicketDAO.class);

    @Value("${data.tickets}")
    private String dataFile;

    @PostConstruct
    public void init(){
        loadData(dataFile, Ticket::setId);
        log.info(data.size() + " tickets were loaded from " + dataFile);
    }

    @Override
    protected Ticket from(String fileLine) {
        var data = fileLine.split(",");
        return new TicketImpl(Long.parseLong(data[0]),Long.parseLong(data[1]), Ticket.Category.valueOf(data[2].toUpperCase()), Integer.parseInt(data[3]));
    }
}
