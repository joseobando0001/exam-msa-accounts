package com.pichincha.exam.accounts.service.impl;

import com.pichincha.exam.accounts.domain.entity.Account;
import com.pichincha.exam.accounts.domain.entity.Movement;
import com.pichincha.exam.accounts.domain.entity.TypeMovement;
import com.pichincha.exam.accounts.helper.MovementMapper;
import com.pichincha.exam.accounts.repository.AccountRepository;
import com.pichincha.exam.accounts.repository.MovementRepository;
import com.pichincha.exam.accounts.service.ReportService;
import com.pichincha.exam.accounts.util.Util;
import com.pichincha.exam.models.MovementFilter;
import com.pichincha.exam.users.CustomerApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.pichincha.exam.accounts.constants.Constants.TYPE_FILE;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final CustomerApi customerApi;
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    @Override
    public Mono<byte[]> getReportByFilter(String clientId, LocalDate startDate, LocalDate endDate, String reportType) {
        log.info("Get report startDate {} endDate {} clientId {} ", startDate, endDate, clientId);

        return reactiveCircuitBreaker.run(
                customerApi.getCustomerById(clientId)
                        .flatMap(client ->
                                accountRepository.findAllByClientId(clientId)
                                        .flatMap(account -> movementRepository.findAllByDateBetweenAndAccountId(
                                                        startDate, endDate, String.valueOf(account.getId()))
                                                .flatMap(movement -> buildMovementResponse(movement, account, client.getNames()))
                                        )
                                        .collectList()
                                        .flatMap(movementList ->
                                                reportType.equalsIgnoreCase(TYPE_FILE) ? Util.generatePdfReport(Flux.fromIterable(movementList)) :
                                                        Util.generateExcelReport(Flux.fromIterable(movementList))
                                        )
                                        .doOnError(throwable -> log.error("Error for get the movements {}", throwable.getMessage()))
                        ));
    }


    private Flux<MovementFilter> buildMovementResponse(Movement movement, Account account, String names) {
        BigDecimal newBalance = movement.getType().equals(TypeMovement.CREDIT)
                ? movement.getBalance().subtract(movement.getAmount())
                : movement.getBalance().add(movement.getAmount());

        MovementFilter movementFilter = MovementMapper.INSTANCE.entityToMovementFilter(movement);
        movementFilter.setAvailableBalance(movement.getBalance());
        movementFilter.setInitBalance(newBalance.intValue() < 0 ? BigDecimal.ZERO : newBalance);
        movementFilter.setType(MovementFilter.TypeEnum.valueOf(account.getType().name()));
        movementFilter.setTypeMovement(MovementFilter.TypeMovementEnum.valueOf(movement.getType().name()));
        movementFilter.setStatus(account.getStatus());
        movementFilter.setAccountNumber(account.getNumber());
        movementFilter.setClient(names);
        return Flux.just(movementFilter);
    }
}
