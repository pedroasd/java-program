package com.pedro.service;

import com.pedro.dao.UserAccountDAO;
import com.pedro.dao.UserDAO;
import com.pedro.model.User;
import com.pedro.model.UserAccount;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    private final Log log = LogFactory.getLog(EventService.class);

    private UserAccountDAO userAccountDAO;

    private UserDAO userDAO;

    public UserAccountService(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public UserAccount getUserAccount(long userId) {
        return userAccountDAO.findByUserId(userId);
    }

    public UserAccount createUserAccount(User user) {
        return userAccountDAO.save(new UserAccount(user, 0L));
    }

    public UserAccount refillAccount(long userId, Long amount) {
        UserAccount account = getUserAccount(userId);
        account.refill(amount);
        UserAccount updated =  userAccountDAO.save(account);
        log.info("User account refilled with " + amount);
        return updated;
    }

    public UserAccount withdrawMoney(long userId, Long amount) {
        var account = getUserAccount(userId);
        if (account.getBalance() >= amount) {
            account.withdraw(amount);
            UserAccount updated =  userAccountDAO.save(account);
            log.info("Account withdraw of " + amount);
            return updated;
        } else {
            throw new IllegalStateException("Insufficient funds to book this ticket.");
        }
    }
}
