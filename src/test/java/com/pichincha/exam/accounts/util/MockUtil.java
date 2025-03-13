package com.pichincha.exam.accounts.util;

import com.pichincha.exam.accounts.domain.entity.Account;
import com.pichincha.exam.accounts.domain.entity.Movement;
import com.pichincha.exam.accounts.domain.entity.TypeAccount;
import com.pichincha.exam.accounts.domain.entity.TypeMovement;
import com.pichincha.exam.users.model.Client;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MockUtil {
    private MockUtil() {

    }

    public static Client buildClient() {
        Client client = new Client();
        client.setAddress("SAN JUAN");
        client.setNames("JOSE RODOLFO");
        client.setPassword("Jose123");
        client.setPhone("0986492314");
        client.setStatus(Boolean.TRUE);
        return client;

    }

    public static Account buildAccount() {
        Account account = new Account();
        account.setInitialValue(BigDecimal.ONE);
        account.setStatus(Boolean.TRUE);
        account.setClientId(1L);
        account.setType(TypeAccount.SAVINGS);
        account.setNumber("12345678");
        return account;
    }

    public static Movement buildMovementForCredit() {
        Movement movement = new Movement();
        movement.setAccountId(1L);
        movement.setDate(LocalDate.now());
        movement.setType(TypeMovement.CREDIT);
        movement.setAmount(BigDecimal.ONE);
        movement.setBalance(BigDecimal.TEN);
        return movement;
    }

    public static Movement buildMovementForDebit() {
        Movement movement = new Movement();
        movement.setAccountId(1L);
        movement.setDate(LocalDate.now());
        movement.setType(TypeMovement.DEBIT);
        movement.setAmount(BigDecimal.valueOf(100));
        movement.setBalance(BigDecimal.valueOf(200));
        return movement;
    }
}
