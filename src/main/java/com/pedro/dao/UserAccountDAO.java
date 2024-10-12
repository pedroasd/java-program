package com.pedro.dao;

import com.pedro.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountDAO extends JpaRepository<UserAccount, Long> {

    UserAccount findByUserId(Long userId);
}