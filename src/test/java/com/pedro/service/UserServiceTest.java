package com.pedro.service;

import com.pedro.model.User;
import com.pedro.model.impl.UserImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/context-config.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void create(){
        var email = "pedro123@email.com";
        User user = new UserImpl("Pedro", email);
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
        User user = new UserImpl("Pedro", email);
        var createdUser = userService.createUser(user);
        var updatedUser = userService.updateUser(new UserImpl(createdUser.getId(), "Pedro M", email));
        var storedUser = userService.getUserById(createdUser.getId());
        assertEquals(updatedUser, storedUser);
    }

    @Test
    public void delete(){
        User user = new UserImpl("Pedro", "pedro@email.com");
        var createdUser = userService.createUser(user);
        var storedUser = userService.getUserById(createdUser.getId());
        assertEquals(createdUser, storedUser);

        var isUserDeleted = userService.deleteUser(createdUser.getId());
        assertTrue(isUserDeleted);
        assertNull(userService.getUserById(createdUser.getId()));
    }

    @Test
    public void list(){
        var u1 = userService.createUser(new UserImpl("Jose", "jose@email.com"));
        var u2 = userService.createUser(new UserImpl("Josefina", "josefina@email.com"));
        var u3 = userService.createUser(new UserImpl("Maria Jose", "majo@email.com"));
        userService.createUser(new UserImpl("Andrea", "andrea@email.com"));

        var users = userService.getUsersByName("jos", 5, 1);
        assertTrue(users.containsAll(List.of(u1, u2, u3)));
    }
}
