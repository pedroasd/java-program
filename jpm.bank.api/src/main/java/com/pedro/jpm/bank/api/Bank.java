package com.pedro.jpm.bank.api;

import com.pedro.jpm.dto.BankCard;
import com.pedro.jpm.dto.BankCardType;
import com.pedro.jpm.dto.User;

public interface Bank {
    BankCard createBankCard(User user, BankCardType bankCardType);

}
