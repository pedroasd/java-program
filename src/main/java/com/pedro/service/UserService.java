package com.pedro.service;

import com.pedro.dao.UserDAO;
import com.pedro.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Log log = LogFactory.getLog(UserService.class);

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Gets user by its id.
     *
     * @return User.
     */
    public User getUserById(long userId) {
        return userDAO.findById(userId).orElse(null);
    }

    /**
     * Gets user by its email. Email is strictly matched.
     *
     * @return User.
     */
    public User getUserByEmail(String email) {
        return userDAO.findFirstByEmail(email).orElse(null);
    }

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param name     Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     */
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        var a = userDAO.findAll();
        var b = userDAO.findAllByNameIgnoreCaseContains(name, PageRequest.of(pageNum - 1, pageSize)).getContent();
        return userDAO.findAllByNameIgnoreCaseContains(name, PageRequest.of(pageNum - 1, pageSize)).getContent();
    }

    /**
     * Creates new user. User id should be auto-generated.
     *
     * @param user User data.
     * @return Created User object.
     */
    public User createUser(User user) {
        var createdUser = userDAO.save(user);
        log.info("User name: " + user.getName() + " was created with id: " + createdUser.getId());
        return createdUser;
    }

    /**
     * Updates user using given data.
     *
     * @param user User data for update. Should have id set.
     * @return Updated User object.
     */
    public User updateUser(User user) {
        var updatedUser = userDAO.save(user);
        log.info("User name: " + user.getName() + " was update with id: " + user.getId());
        return updatedUser;
    }

    /**
     * Deletes user by its id.
     *
     * @param userId User id.
     * @return Flag that shows whether user has been deleted.
     */
    public boolean deleteUser(long userId) {
        return userDAO.findById(userId).map(e -> {
            userDAO.deleteById(userId);
            log.info("User id: " + userId + " was deleted.");
            return true;
        }).orElse(false);
    }
}
