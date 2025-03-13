package com.pichincha.exam.accounts.service;

import com.pichincha.exam.accounts.domain.entity.Movement;
import com.pichincha.exam.accounts.domain.entity.TypeMovement;
import com.pichincha.exam.accounts.exception.DuplicateAccount;
import com.pichincha.exam.accounts.helper.AccountMapper;
import com.pichincha.exam.accounts.repository.AccountRepository;
import com.pichincha.exam.accounts.repository.MovementRepository;
import com.pichincha.exam.models.Account;
import com.pichincha.exam.users.CustomerApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.pichincha.exam.accounts.constants.ErrorConstants.UNAVAILABLE;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final CustomerApi customerApi;
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    @Override
    public Flux<Account> getAccountByFilter() {
        log.info("Get accounts");
        return accountRepository.findAllByStatus(Boolean.TRUE)
                .map(AccountMapper.INSTANCE::accountEntityToDto)
                .doOnError(throwable -> log.error("Error for get accounts"));
    }

    @Override
    public Mono<Account> getAccountByAccountId(String accountId) {
        log.info("Get account by number {}", maskAccount(accountId));
        return accountRepository.findByNumber(accountId)
                .map(AccountMapper.INSTANCE::accountEntityToDto)
                .doOnError(throwable -> log.error("Error for search {}", throwable.getMessage()));
    }

    @Override
    public Mono<Account> postAccount(Account account) {
        log.info("Post account {}", maskAccount(account.getAccountNumber()));
        return customerApi.getCustomerById(account.getClient())
                .flatMap(client ->
                        accountRepository.save(AccountMapper.INSTANCE.accountDtoToEntity(account))
                                .onErrorMap(throwable -> new DuplicateAccount(UNAVAILABLE))
                                .flatMap(acc -> {
                                    Movement movement = new Movement();
                                    movement.setType(TypeMovement.CREDIT);
                                    movement.setBalance(account.getInitialBalance());
                                    movement.setDate(LocalDate.now());
                                    movement.setAccountId(acc.getId());
                                    movement.setAmount(account.getInitialBalance());
                                    return movementRepository.save(movement)
                                            .map(entity -> {
                                                Account dto = AccountMapper.INSTANCE.accountEntityToDto(acc);
                                                dto.setClient(client.getNames());
                                                return dto;
                                            });
                                })
                )
                .doOnError(throwable -> log.error("Error for service customer {}", throwable.getMessage()));
    }

    private String maskAccount(String accountId) {
        String firstDigits = accountId.substring(0, 2);
        String lastDigits = accountId.substring(accountId.length() - 2);
        return firstDigits.formatted(
                "*".repeat(Math.max(0, accountId.length() - 4)),
                lastDigits);
    }
}
