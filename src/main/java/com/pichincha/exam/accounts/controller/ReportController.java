package com.pichincha.exam.accounts.controller;

import com.pichincha.exam.accounts.service.ReportService;
import com.pichincha.exam.api.ReportsApi;
import com.pichincha.exam.models.MovementFilter;
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
public class ReportController implements ReportsApi {
    private final ReportService reportService;

    @Override
    public Mono<ResponseEntity<Flux<MovementFilter>>> getReportByFilter(String clientId, LocalDate startDate, LocalDate endDate, ServerWebExchange exchange) {
        log.info("Get report");
        return reportService.getMovementByFilter(clientId, startDate, endDate)
                .collectList()
                .map(accounts -> ResponseEntity.ok().body(Flux.fromIterable(accounts)));
    }
}
