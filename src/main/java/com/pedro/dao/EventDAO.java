package com.pedro.dao;

import com.pedro.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EventDAO extends JpaRepository<Event, Long> {

    Page<Event> findAllByTitleIgnoreCaseContains(String title, Pageable pageable);

    Page<Event> findAllByDate(Date date, Pageable pageable);
}
