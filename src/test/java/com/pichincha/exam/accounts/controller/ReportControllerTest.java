package com.pichincha.exam.accounts.controller;

import com.pichincha.exam.accounts.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @InjectMocks
    ReportController reportController;

    @Mock
    ReportService reportService;

    @Test
    void getReportByFilter() {
        when(reportService.getReportByFilter(any(), any(), any(), any())).thenReturn(Mono.just("data".getBytes(StandardCharsets.UTF_8)));
        StepVerifier.create(reportController.getReportByFilter("pdf", "1", LocalDate.now(), LocalDate.now(),
                        MockServerWebExchange.from(MockServerHttpRequest.get("http://localhost:8080/api/v1/reports").build())))
                .expectNextMatches(clientResponseEntity -> clientResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
                .verify()
        ;
    }
}