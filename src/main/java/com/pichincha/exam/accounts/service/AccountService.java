package com.pichincha.exam.accounts.service;

import com.pichincha.exam.models.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> getAccountByFilter();

    Mono<Account> getAccountByAccountId(String accountId);

    Mono<Account> postAccount(Account account);
}
