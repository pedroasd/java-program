package com.pedro.jmp.cloud.bank.impl;

import com.pedro.jpm.bank.api.Bank;
import com.pedro.jpm.dto.BankCardType;
import com.pedro.jpm.dto.CreditBankCard;
import com.pedro.jpm.dto.DebitBankCard;
import com.pedro.jpm.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class BankTest {

    PodamFactory factory = new PodamFactoryImpl();

    @ParameterizedTest
    @MethodSource("bankImplementations")
    @DisplayName("Create a bank card successfully")
    public void createBankCardTest(Bank bank){
        var user1 = factory.manufacturePojo(User.class);
        var user2 = factory.manufacturePojo(User.class);

        var debitCard = bank.createBankCard(user1, BankCardType.DEBIT);
        var creditCard = bank.createBankCard(user2, BankCardType.CREDIT);

        assertInstanceOf(DebitBankCard.class, debitCard);
        assertEquals(user1, debitCard.getUser());

        assertInstanceOf(CreditBankCard.class, creditCard);
        assertEquals(user2, creditCard.getUser());
    }

    private static Stream<Arguments> bankImplementations() {
        return Stream.of(
                Arguments.of(Named.of("Central Bank", new CentralBank())),
                Arguments.of(Named.of("Investment Bank", new InvestmentBank())),
                Arguments.of(Named.of("Retail Bank", new RetailBank()))
        );
    }
}
