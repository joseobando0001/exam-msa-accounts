package com.pichincha.exam.accounts.repository;

import com.pichincha.exam.accounts.domain.entity.Movement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {
    Flux<Movement> findAllByDateBetweenAndAccountId(LocalDate startDate, LocalDate endDate, String accountId);
}
