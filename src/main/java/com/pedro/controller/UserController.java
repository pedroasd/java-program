package com.pedro.controller;

import com.pedro.model.User;
import com.pedro.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/")
    public String showIndex(Model model) {
        return "index";
    }

    // Display the form to create a new user
    @GetMapping("/users/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    // Handle form submission to create a new user
    @PostMapping("/users")
    public String createUser(User user) {
        bookingFacade.createUser(user);
        return "redirect:/users/new?success";
    }

    // Display the form to search for users by name
    @GetMapping("/users/search")
    public String showSearchUsersForm() {
        return "searchUsers";
    }

    // Handle the search and display the results
    @GetMapping("/users")
    public String getUsersByName(@RequestParam(value = "name", required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            List<User> users = bookingFacade.getUsersByName(name, 100, 1);
            model.addAttribute("users", users);
            model.addAttribute("searchName", name);
        } else {
            model.addAttribute("users", null);
            model.addAttribute("searchName", "");
        }
        return "searchUsers";
    }
}

