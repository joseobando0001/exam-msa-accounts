package com.pichincha.exam.accounts.service;


import com.pichincha.exam.models.MovementMessage;
import com.pichincha.exam.models.MovementRequest;
import reactor.core.publisher.Mono;

public interface MovementService {

    Mono<MovementMessage> postMovement(MovementRequest movement);

}
