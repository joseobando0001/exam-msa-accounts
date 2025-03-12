package com.pichincha.exam.accounts.repository;

import com.pichincha.exam.accounts.domain.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

}
