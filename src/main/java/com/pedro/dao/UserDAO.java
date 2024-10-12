package com.pedro.dao;

import com.pedro.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);

    Page<User> findAllByNameIgnoreCaseContains(String name, Pageable pageable);
}
