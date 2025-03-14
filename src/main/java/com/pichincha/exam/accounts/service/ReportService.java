package com.pichincha.exam.accounts.service;

import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ReportService {
    Mono<byte[]> getReportByFilter(String clientId, LocalDate startDate, LocalDate endDate, String reportType);

}
