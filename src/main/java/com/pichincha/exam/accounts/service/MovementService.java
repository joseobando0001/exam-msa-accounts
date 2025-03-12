package com.pichincha.exam.accounts.service;


import com.pichincha.exam.models.MovementFilter;
import com.pichincha.exam.models.MovementMessage;
import com.pichincha.exam.models.MovementRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface MovementService {

    Flux<MovementFilter> getMovementByFilter(String clientId, LocalDate startDate, LocalDate endDate);

    Mono<MovementMessage> postMovement(MovementRequest movement);

}
