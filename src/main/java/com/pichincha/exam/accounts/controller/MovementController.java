package com.pichincha.exam.accounts.controller;

import com.pichincha.exam.accounts.service.MovementService;
import com.pichincha.exam.api.MovementsApi;
import com.pichincha.exam.models.MovementMessage;
import com.pichincha.exam.models.MovementRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovementController implements MovementsApi {
    private final MovementService movementService;

    @Override
    public Mono<ResponseEntity<MovementMessage>> postMovement(Mono<MovementRequest> movementRequest, ServerWebExchange exchange) {
        log.info("Create movement");
        return movementRequest.flatMap(body ->
                movementService.postMovement(body)
                        .map(movementMessage -> ResponseEntity.ok().body(movementMessage)));
    }
}
