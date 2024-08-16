package com.pedro.jmp.service.api;

import com.pedro.jpm.dto.BankCard;
import com.pedro.jpm.dto.Subscription;
import com.pedro.jpm.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber);

    List<User> getAllUsers();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);

    default double getAverageUsersAge() {
        return getAllUsers()
                .stream()
                .map(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
                .mapToLong(n -> n)
                .average()
                .orElse(0);
    }

    static boolean isPayableUser(User user) {
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) > 18;
    }
}
