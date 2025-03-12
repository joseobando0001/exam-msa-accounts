package com.pichincha.exam.accounts.service;

import com.pichincha.exam.accounts.helper.AccountMapper;
import com.pichincha.exam.accounts.repository.AccountRepository;
import com.pichincha.exam.models.Account;
import com.pichincha.exam.users.CustomerApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final CustomerApi customerApi;
    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> getAccountByFilter() {
        log.info("Get accounts");
        return accountRepository.findAllByStatus(Boolean.TRUE)
                .map(AccountMapper.INSTANCE::accountEntityToDto)
                .doOnError(throwable -> log.error("Error for get accounts"));
    }

    @Override
    public Mono<Account> getAccountByAccountId(String accountId) {
        return null;
    }

    @Override
    public Mono<Account> postAccount(Account account) {
        log.info("Post account {}", account);
        return customerApi.getCustomerById(account.getClient())
                .flatMap(client ->
                        accountRepository.save(
                                        AccountMapper.INSTANCE.accountDtoToEntity(account))
                                .map(entity -> {
                                    Account dto = AccountMapper.INSTANCE.accountEntityToDto(entity);
                                    dto.setClient(client.getNames());
                                    return dto;
                                }))
                .doOnError(throwable -> log.error("Error for service customer {}", throwable.getMessage()));
    }
}
