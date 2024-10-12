package com.pedro.service;

import com.pedro.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void create(){
        var email = "pedro123@email.com";
        User user = new User("Pedro", email);
        var createdUser = userService.createUser(user);
        user.setId(createdUser.getId());
        assertTrue(createdUser.getId() > 0);
        assertEquals(user, createdUser);

        assertEquals(createdUser, userService.getUserById(createdUser.getId()));
        assertEquals(createdUser, userService.getUserByEmail(email));
    }

    @Test
    public void update(){
        var email = "pedro@email.com";
        User user = new User("Pedro", email);
        var createdUser = userService.createUser(user);
        var updatedUser = userService.updateUser(new User(createdUser.getId(), "Pedro M", email));
        var storedUser = userService.getUserById(createdUser.getId());
        assertEquals(updatedUser, storedUser);
    }

    @Test
    public void delete(){
        User user = new User("Pedro", "pedro@email.com");
        var createdUser = userService.createUser(user);
        var storedUser = userService.getUserById(createdUser.getId());
        assertEquals(createdUser, storedUser);

        var isUserDeleted = userService.deleteUser(createdUser.getId());
        assertTrue(isUserDeleted);
        assertNull(userService.getUserById(createdUser.getId()));
    }

    @Test
    public void list(){
        var u1 = userService.createUser(new User("Jose", "jose@email.com"));
        var u2 = userService.createUser(new User("Josefina", "josefina@email.com"));
        var u3 = userService.createUser(new User("Maria Jose", "majo@email.com"));
        userService.createUser(new User("Andrea", "andrea@email.com"));

        var users = userService.getUsersByName("jos", 5, 1);
        assertTrue(users.containsAll(List.of(u1, u2, u3)));
    }
}
