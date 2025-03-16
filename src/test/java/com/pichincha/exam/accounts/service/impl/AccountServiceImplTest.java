package com.pichincha.exam.accounts.service.impl;

import com.pichincha.exam.accounts.repository.AccountRepository;
import com.pichincha.exam.accounts.repository.MovementRepository;
import com.pichincha.exam.users.CustomerApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.accounts.util.MockUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    CustomerApi customerApi;
    @Mock

    AccountRepository accountRepository;

    @Mock
    MovementRepository movementRepository;

    @Mock
    TransactionalOperator transactionalOperator;

    @Test
    void getAccountByFilter() {
        when(accountRepository.findAllByStatus(any())).thenReturn(Flux.just(buildAccount()));
        StepVerifier.create(accountService.getAccountByFilter())
                .expectNextMatches(account -> !account.getAccountNumber().isEmpty())
                .expectComplete()
                .verify();
    }

    @Test
    void getAccountByAccountId() {
        when(accountRepository.findByNumber(any())).thenReturn(Mono.just(buildAccount()));
        StepVerifier.create(accountService.getAccountByAccountId("12345678"))
                .expectNextMatches(account -> !account.getAccountNumber().isEmpty())
                .expectComplete()
                .verify();

    }

    @Test
    void postAccount() {
        when(customerApi.getCustomerById(any())).thenReturn(Mono.just(buildClient()));
        when(accountRepository.save(any())).thenReturn(Mono.just(buildAccount()));
        when(movementRepository.save(any())).thenReturn(Mono.just(buildMovementForCredit()));
        when(transactionalOperator.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        StepVerifier.create(accountService.postAccount(buildAccountDto()))
                .expectNextMatches(account -> !account.getAccountNumber().isEmpty())
                .expectComplete()
                .verify();
    }

}