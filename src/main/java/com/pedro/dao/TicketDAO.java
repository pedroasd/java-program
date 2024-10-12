package com.pedro.dao;

import com.pedro.model.Event;
import com.pedro.model.Ticket;
import com.pedro.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDAO extends JpaRepository<Ticket, Long> {

    Page<Ticket> findAllByUser(User user, Pageable pageable);

    Page<Ticket> findAllByEvent(Event event, Pageable pageable);
}
