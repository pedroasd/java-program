package com.pedro.application;

import com.pedro.jmp.cloud.bank.impl.InvestmentBank;
import com.pedro.jmp.cloud.service.impl.ServiceImpl;
import com.pedro.jmp.service.api.Service;
import com.pedro.jpm.bank.api.Bank;
import com.pedro.jpm.dto.BankCardType;
import com.pedro.jpm.dto.CreditBankCard;
import com.pedro.jpm.dto.DebitBankCard;
import com.pedro.jpm.dto.User;
import com.pedro.jpm.dto.SubscriptionNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest{

    PodamFactory factory = new PodamFactoryImpl();

    @Test
    @DisplayName("Create some cards, subscribe the cards and check the subscriptions were successful")
    public void successfulTest(){
        var user1 = factory.manufacturePojo(User.class);
        var user2 = factory.manufacturePojo(User.class);

        Bank bank = ServiceLoader.load(Bank.class).findFirst().orElseThrow();
        var bankCard1 = bank.createBankCard(user1, BankCardType.DEBIT);
        var bankCard2 = bank.createBankCard(user1, BankCardType.CREDIT);
        var bankCard3 = bank.createBankCard(user2, BankCardType.DEBIT);

        assertEquals(user1, bankCard1.getUser());
        assertInstanceOf(DebitBankCard.class, bankCard1);
        assertEquals(user1, bankCard2.getUser());
        assertInstanceOf(CreditBankCard.class, bankCard2);
        assertEquals(user2, bankCard3.getUser());
        assertInstanceOf(DebitBankCard.class, bankCard3);

        Service service = ServiceLoader.load(Service.class).findFirst().orElseThrow();
        service.subscribe(bankCard1);
        service.subscribe(bankCard2);
        service.subscribe(bankCard3);

        var subscription1 = service.getSubscriptionByBankCardNumber(bankCard1.getNumber());
        var subscription2 = service.getSubscriptionByBankCardNumber(bankCard2.getNumber());
        var subscription3 = service.getSubscriptionByBankCardNumber(bankCard3.getNumber());

        assertEquals(bankCard1.getNumber(), subscription1.orElseThrow(SubscriptionNotFoundException::new).getBankcardNumber());
        assertEquals(bankCard2.getNumber(), subscription2.orElseThrow(SubscriptionNotFoundException::new).getBankcardNumber());
        assertEquals(bankCard3.getNumber(), subscription3.orElseThrow(SubscriptionNotFoundException::new).getBankcardNumber());

        // Just two user spite of multiple subscriptions.
        var users = service.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));

        var subscriptions = service.getAllSubscriptionsByCondition(s -> bankCard1.getNumber().equals(s.getBankcardNumber()) || bankCard2.getNumber().equals(s.getBankcardNumber()));
        assertEquals(2, subscriptions.size());
        assertTrue(subscriptions.stream().anyMatch(s -> bankCard1.getNumber().equals(s.getBankcardNumber())));
        assertTrue(subscriptions.stream().anyMatch(s -> bankCard2.getNumber().equals(s.getBankcardNumber())));
    }
}
