package com.pichincha.exam.accounts.service.impl;

import com.pichincha.exam.accounts.domain.entity.TypeMovement;
import com.pichincha.exam.accounts.helper.MovementMapper;
import com.pichincha.exam.accounts.repository.AccountRepository;
import com.pichincha.exam.accounts.repository.MovementRepository;
import com.pichincha.exam.accounts.service.ReportService;
import com.pichincha.exam.models.MovementFilter;
import com.pichincha.exam.users.CustomerApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final CustomerApi customerApi;
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    @Override
    public Flux<MovementFilter> getMovementByFilter(String clientId, LocalDate startDate, LocalDate endDate) {
        log.info("Get report startDate {} endDate {} clientId {} ", startDate, endDate, clientId);
        return customerApi.getCustomerById(clientId)
                .flatMapMany(client ->
                        accountRepository.findAllByClientId(clientId)
                                .flatMap(account ->
                                        movementRepository.findAllByDateBetweenAndAccountId(
                                                        startDate,
                                                        endDate,
                                                        String.valueOf(account.getId()))
                                                .flatMap(movement -> {

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
                                                    movementFilter.setClient(client.getNames());
                                                    return Flux.just(movementFilter);
                                                }))
                );
    }
}
