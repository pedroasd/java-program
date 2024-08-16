package com.pedro.jmp.cloud.service.impl;

import com.pedro.jmp.service.api.Service;
import com.pedro.jpm.dto.BankCard;
import com.pedro.jpm.dto.Subscription;
import com.pedro.jpm.dto.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private final Map<String, User> subscribedUsers = new HashMap<>();
    private final Map<String, Subscription> subscriptions = new HashMap<>();

    @Override
    public void subscribe(BankCard bankCard) {
        subscribedUsers.put(bankCard.getNumber(), bankCard.getUser());
        subscriptions.put(bankCard.getNumber(), new Subscription(bankCard.getNumber(), LocalDate.now()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) {
        return Optional.ofNullable(subscriptions.get(bankCardNumber));
    }

    @Override
    public List<User> getAllUsers() {
        return subscribedUsers.values().stream().distinct().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return subscriptions.values().stream().filter(predicate).collect(Collectors.toUnmodifiableList());
    }
}
