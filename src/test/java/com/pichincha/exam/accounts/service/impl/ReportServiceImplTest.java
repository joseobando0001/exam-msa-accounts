package com.pichincha.exam.accounts.service.impl;

import com.pichincha.exam.accounts.repository.AccountRepository;
import com.pichincha.exam.accounts.repository.MovementRepository;
import com.pichincha.exam.users.CustomerApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static com.pichincha.exam.accounts.util.MockUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @InjectMocks
    ReportServiceImpl reportService;

    @Mock
    CustomerApi customerApi;

    @Mock
    AccountRepository accountRepository;

    @Mock
    MovementRepository movementRepository;

    @Test
    void getMovementByFilterForCreditAndPdf() {
        when(customerApi.getCustomerById(any())).thenReturn(Mono.just(buildClient()));
        when(accountRepository.findAllByClientId(any())).thenReturn(Flux.just(buildAccount()));
        when(movementRepository.findAllByDateBetweenAndAccountId(any(), any(), any())).thenReturn(Flux.just(buildMovementForCredit()));
        StepVerifier.create(reportService.getReportByFilter("1", LocalDate.now(), LocalDate.now(), "pdf"))
                .expectNextMatches(bytes -> bytes.length > 0)
                .expectComplete().verify();
    }

    @Test
    void getMovementByFilterForDebitAndExcel() {
        when(customerApi.getCustomerById(any())).thenReturn(Mono.just(buildClient()));
        when(accountRepository.findAllByClientId(any())).thenReturn(Flux.just(buildAccount()));
        when(movementRepository.findAllByDateBetweenAndAccountId(any(), any(), any())).thenReturn(Flux.just(buildMovementForDebit()));
        StepVerifier.create(reportService.getReportByFilter("1", LocalDate.now(), LocalDate.now(), "xlsx"))
                .expectNextMatches(bytes -> bytes.length > 0)
                .expectComplete().verify();
    }
}