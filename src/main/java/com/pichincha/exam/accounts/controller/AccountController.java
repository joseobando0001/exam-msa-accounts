package com.pichincha.exam.accounts.controller;

import com.pichincha.exam.accounts.service.AccountService;
import com.pichincha.exam.api.AccountsApi;
import com.pichincha.exam.models.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController implements AccountsApi {
    private final AccountService accountService;

    @Override
    public Mono<ResponseEntity<Flux<Account>>> getAccountByFilter(ServerWebExchange exchange) {
        log.info("Accounts for obtained");
        return accountService.getAccountByFilter()
                .collectList()
                .map(accounts -> ResponseEntity.ok().body(Flux.fromIterable(accounts)));
    }

    @Override
    public Mono<ResponseEntity<Account>> getAccountByAccountId(String accountId, ServerWebExchange exchange) {
        log.info("Accounts for filter accountId");
        return accountService.getAccountByAccountId(accountId)
                .map(account -> ResponseEntity.ok().body(account));
    }

    @Override
    public Mono<ResponseEntity<Account>> postAccount(Mono<Account> account, ServerWebExchange exchange) {
        log.info("Account for create");
        return account.flatMap(body ->
                accountService.postAccount(body)
                        .map(response -> ResponseEntity.ok().body(response))
        );
    }
}
