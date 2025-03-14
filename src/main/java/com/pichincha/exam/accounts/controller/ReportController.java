package com.pichincha.exam.accounts.controller;

import com.pichincha.exam.accounts.service.ReportService;
import com.pichincha.exam.api.ReportsApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static com.pichincha.exam.accounts.constants.Constants.FILENAME;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReportController implements ReportsApi {
    private final ReportService reportService;

    @Override
    public Mono<ResponseEntity<byte[]>> getReportByFilter(String reportType, String clientId, LocalDate startDate, LocalDate endDate, ServerWebExchange exchange) {
        log.info("Get report with : {}", FILENAME.concat(reportType));
        return reportService.getReportByFilter(clientId, startDate, endDate, reportType)
                .map(report -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, FILENAME.concat(reportType))
                        .body(report));
    }
}
