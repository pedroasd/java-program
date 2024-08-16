package com.pedro.jmp.cloud.service.impl;


import com.pedro.jpm.dto.SubscriptionNotFoundException;
import com.pedro.jpm.dto.CreditBankCard;
import com.pedro.jpm.dto.DebitBankCard;
import com.pedro.jpm.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceImplTest {

    private final PodamFactory factory = new PodamFactoryImpl();

    @Test
    @DisplayName("Subscribe some bank cards successfully")
    public void subscribeCardsSuccessfully(){
        var user1 = factory.manufacturePojo(User.class);
        var user2 = factory.manufacturePojo(User.class);

        var bankCard1 = new DebitBankCard(generateCardNumber(), user1);
        var bankCard2 = new CreditBankCard(generateCardNumber(), user1);
        var bankCard3 = new DebitBankCard(generateCardNumber(), user1);
        var bankCard4 = new CreditBankCard(generateCardNumber(), user2);

        var service = new ServiceImpl();
        var bankCards = List.of(bankCard1, bankCard2, bankCard3, bankCard4);
        bankCards.forEach(service::subscribe);

        // All subscriptions were success and starDate should be lesser or equal to 1, due to it is a Date.
        bankCards.forEach(bc -> {
                var subscription = service.getSubscriptionByBankCardNumber(bc.getNumber());
                assertEquals(bc.getNumber(), subscription.orElseThrow(SubscriptionNotFoundException::new).getBankcardNumber());
                assertTrue(Period.between(subscription.orElseThrow(SubscriptionNotFoundException::new).getStartDate(), LocalDate.now()).getDays() <= 1);
        });

        // Just two user spite of multiple subscriptions.
        var users = service.getAllUsers();
        assertIterableEquals(List.of(user1, user2), users);
    }

    @Test
    @DisplayName("Get subscription not found")
    public void getSubscriptionNotFound() {
        var service = new ServiceImpl();
        var subscription = service.getSubscriptionByBankCardNumber("123");
        assertTrue(subscription.isEmpty());
    }

    @Test
    @DisplayName("Get empty users list")
    public void getEmptyUsersList() {
        var service = new ServiceImpl();
        var users = service.getAllUsers();
        assertTrue(users.isEmpty());
    }

    private String generateCardNumber() {
        var random = new Random();
        return random.longs(1111111111111111L, 9999999999999999L).toString();
    }
}
