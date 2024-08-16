package com.pedro.jmp.cloud.bank.impl;

import com.pedro.jpm.bank.api.Bank;
import com.pedro.jpm.dto.*;

import java.util.Random;

public class InvestmentBank implements Bank {

    public final String CARD_PREFIX = "4502";

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        var random = new Random();
        var cardNumber =  CARD_PREFIX + random.longs(111111111111L, 999999999999L).toString();
        return switch (bankCardType) {
            case DEBIT -> new DebitBankCard(cardNumber, user);
            case CREDIT -> new CreditBankCard(cardNumber, user);
        };
    }
}
