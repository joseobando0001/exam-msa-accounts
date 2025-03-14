package com.pichincha.exam.accounts.service.impl;

import com.pichincha.exam.accounts.repository.AccountRepository;
import com.pichincha.exam.accounts.repository.MovementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.accounts.util.MockUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovementServiceImplTest {


    @InjectMocks
    MovementServiceImpl movementService;

    @Mock
    MovementRepository movementRepository;

    @Mock
    AccountRepository accountRepository;

    @Test
    void postMovementForCredit() {
        when(accountRepository.findByNumber(any())).thenReturn(Mono.just(buildAccount()));
        when(accountRepository.save(any())).thenReturn(Mono.just(buildAccount()));
        when(movementRepository.save(any())).thenReturn(Mono.just(buildMovementForCredit()));
        StepVerifier.create(movementService.postMovement(buildMovementRequestForCredit()))
                .expectNextMatches(movementMessage -> !movementMessage.getMessage().isEmpty())
                .expectComplete()
                .verify();
    }

    @Test
    void postMovementForDebit() {
        when(accountRepository.findByNumber(any())).thenReturn(Mono.just(buildAccount()));
        when(accountRepository.save(any())).thenReturn(Mono.just(buildAccount()));
        when(movementRepository.save(any())).thenReturn(Mono.just(buildMovementForDebit()));
        StepVerifier.create(movementService.postMovement(buildMovementRequestForDebit()))
                .expectNextMatches(movementMessage -> !movementMessage.getMessage().isEmpty())
                .expectComplete()
                .verify();
    }

    @Test
    void postMovementForFundsUnavailable() {
        when(accountRepository.findByNumber(any())).thenReturn(Mono.just(buildAccount()));
        StepVerifier.create(movementService.postMovement(buildMovementRequestForDebitError()))
                .expectErrorMatches(movementMessage -> !movementMessage.getMessage().isEmpty())
                .verify();
    }
}