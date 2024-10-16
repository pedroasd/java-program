package com.pedro.controller;

import com.pedro.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private BookingFacade bookingFacade;

    @GetMapping("/refillAccount")
    public String showRefillForm(@RequestParam("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "refillAccount";
    }

    @PostMapping("/refillAccount")
    public String refillAccount(@RequestParam("userId") Long userId,
                                @RequestParam("amount") Long amount,
                                Model model) {
        bookingFacade.refillAccount(userId, amount);
        model.addAttribute("message", "Account refilled successfully!");
        return "refillAccount";
    }
}

