package com.pedro.controller;

import com.pedro.model.User;
import com.pedro.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private BookingFacade bookingFacade;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(User user) {
        return ResponseEntity.ok(bookingFacade.createUser(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsersByName(@RequestParam(value = "name", required = false) String name, Model model) {
        return ResponseEntity.ok(bookingFacade.getUsersByName(name, 100, 1));
    }
}

