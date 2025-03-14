package com.pichincha.exam.accounts.controller;

import com.pichincha.exam.accounts.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.accounts.util.MockUtil.buildAccountDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @InjectMocks
    AccountController accountController;

    @Mock
    AccountService accountService;

    MockServerHttpRequest request;
    private static final String LOCAL = "http://localhost/accounts";


    @Test
    void getAccountByFilter() {
        when(accountService.getAccountByFilter()).thenReturn(Flux.just(buildAccountDto()));
        request = MockServerHttpRequest.get(LOCAL).build();
        StepVerifier.create(accountController.getAccountByFilter(MockServerWebExchange.from(request)))
                .expectComplete();
    }

    @Test
    void getAccountByAccountId() {
        when(accountService.getAccountByAccountId(any())).thenReturn(Mono.just(buildAccountDto()));
        request = MockServerHttpRequest.get(LOCAL).build();
        StepVerifier.create(accountController.getAccountByAccountId("1", MockServerWebExchange.from(request)))
                .expectComplete();
    }

    @Test
    void postAccount() {
        when(accountService.postAccount(any())).thenReturn(Mono.just(buildAccountDto()));
        request = MockServerHttpRequest.post(LOCAL).build();
        StepVerifier.create(accountController.postAccount(Mono.just(buildAccountDto()), MockServerWebExchange.from(request)))
                .expectNextMatches(accountResponseEntity -> accountResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
                .verify();
    }
}