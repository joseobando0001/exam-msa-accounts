package com.pichincha.exam.accounts.controller;

import com.pichincha.exam.accounts.service.MovementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pichincha.exam.accounts.util.MockUtil.buildMovementMessage;
import static com.pichincha.exam.accounts.util.MockUtil.buildMovementRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovementControllerTest {

    @InjectMocks
    MovementController movementController;

    @Mock
    MovementService movementService;

    @Test
    void postMovement() {
        when(movementService.postMovement(any())).thenReturn(Mono.just(buildMovementMessage()));
        StepVerifier.create(movementController.postMovement(Mono.just(buildMovementRequest()),
                        MockServerWebExchange.from(MockServerHttpRequest.post("http://localhost:8080/api/v1/movement").build())))
                .expectNextMatches(clientResponseEntity -> clientResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
                .verify();
    }
}