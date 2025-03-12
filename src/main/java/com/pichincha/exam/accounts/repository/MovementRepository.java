package com.pichincha.exam.accounts.repository;

import com.pichincha.exam.accounts.domain.entity.Movement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {

}
