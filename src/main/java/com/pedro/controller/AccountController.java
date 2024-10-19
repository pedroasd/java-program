package com.pedro.controller;

import com.pedro.facade.BookingFacade;
import com.pedro.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private BookingFacade bookingFacade;

    @PostMapping("/refillAccount")
    public ResponseEntity<UserAccount> refillAccount(@RequestParam("userId") Long userId,
                                                     @RequestParam("amount") Long amount) {
        return ResponseEntity.ok(bookingFacade.refillAccount(userId, amount));
    }
}

