package com.pichincha.exam.accounts.service;

import com.pichincha.exam.models.MovementFilter;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface ReportService {
    Flux<MovementFilter> getMovementByFilter(String clientId, LocalDate startDate, LocalDate endDate);

}
