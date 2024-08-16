package com.pedro.jmp.service.api;

import com.pedro.jpm.dto.BankCard;
import com.pedro.jpm.dto.Subscription;
import com.pedro.jpm.dto.User;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    PodamFactory factory = new PodamFactoryImpl();

    @Test
    public void getAverageUsersAgeTest(){
        var user1 = factory.manufacturePojo(User.class);
        user1.setBirthday(LocalDate.now().minus(Period.ofYears(40)));
        var user2 = factory.manufacturePojo(User.class);
        user2.setBirthday(LocalDate.now().minus(Period.ofYears(30)));
        var user3 = factory.manufacturePojo(User.class);
        user3.setBirthday(LocalDate.now().minus(Period.ofYears(20)));

        var userList = List.of(user1, user2, user3);
        var expectedAverage = 30;

        Service service = new Service() {
            @Override
            public void subscribe(BankCard bankCard) {}

            @Override
            public Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) {return Optional.empty();}

            @Override
            public List<User> getAllUsers() {
                return userList;
            }

            @Override
            public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {return List.of();}
        };

        var average = service.getAverageUsersAge();
        assertEquals(expectedAverage , average);
    }

    @Test
    public void isPayableUserTest(){
        var user = factory.manufacturePojo(User.class);
        user.setBirthday(LocalDate.now().minus(Period.ofYears(19)));
        assertTrue( Service.isPayableUser(user) );
    }

    @Test
    public void isNotPayableUserTest(){
        var user = factory.manufacturePojo(User.class);
        user.setBirthday(LocalDate.now().minus(Period.ofYears(17)));
        assertFalse( Service.isPayableUser(user) );
    }
}
