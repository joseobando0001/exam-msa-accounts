package com.pichincha.exam.accounts.repository;

import com.pichincha.exam.accounts.domain.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {
    Flux<Account> findAllByStatus(Boolean status);

    Mono<Account> findByNumber(String number);
}
