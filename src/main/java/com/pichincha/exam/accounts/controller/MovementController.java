package com.pichincha.exam.accounts.controller;

import com.pichincha.exam.accounts.service.MovementService;
import com.pichincha.exam.api.MovementsApi;
import com.pichincha.exam.models.MovementFilter;
import com.pichincha.exam.models.MovementMessage;
import com.pichincha.exam.models.MovementRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovementController implements MovementsApi {
    private final MovementService movementService;

    @Override
    public Mono<ResponseEntity<Flux<MovementFilter>>> getMovementByFilter(String clientId, LocalDate startDate, LocalDate endDate, ServerWebExchange exchange) {
        return movementService.getMovementByFilter(clientId, startDate, endDate)
                .collectList()
                .map(accounts -> ResponseEntity.ok().body(Flux.fromIterable(accounts)));
    }

    @Override
    public Mono<ResponseEntity<MovementMessage>> postMovement(Mono<MovementRequest> movementRequest, ServerWebExchange exchange) {
        return movementRequest.flatMap(body ->
                movementService.postMovement(body)
                        .map(movementMessage -> ResponseEntity.ok().body(movementMessage)));
    }
}
