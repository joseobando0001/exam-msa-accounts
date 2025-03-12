package com.pichincha.exam.accounts.service;

import com.pichincha.exam.accounts.exception.FundsUnavailable;
import com.pichincha.exam.accounts.helper.MovementMapper;
import com.pichincha.exam.accounts.repository.AccountRepository;
import com.pichincha.exam.accounts.repository.MovementRepository;
import com.pichincha.exam.models.MovementFilter;
import com.pichincha.exam.models.MovementMessage;
import com.pichincha.exam.models.MovementRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pichincha.exam.accounts.constants.ErrorConstants.NOT_FOUND;
import static com.pichincha.exam.accounts.constants.ErrorConstants.UNAVAILABLE_FUNDS;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Override
    public Flux<MovementFilter> getMovementByFilter(String clientId, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Mono<MovementMessage> postMovement(MovementRequest movement) {
        return accountRepository.findByNumber(movement.getAccountNumber())
                .flatMap(account -> {
                    com.pichincha.exam.accounts.domain.entity.Movement entity = MovementMapper.INSTANCE.movementDtoToEntity(movement);
                    entity.setDate(LocalDateTime.now());
                    if (movement.getType().equals(MovementRequest.TypeEnum.DEBIT) && account.getInitialValue().compareTo(movement.getValue()) < 0) {
                        return Mono.error(new FundsUnavailable(UNAVAILABLE_FUNDS));
                    }
                    BigDecimal newBalance = movement.getType().equals(MovementRequest.TypeEnum.CREDIT)
                            ? account.getInitialValue().add(movement.getValue())
                            : account.getInitialValue().subtract(movement.getValue());
                    account.setInitialValue(newBalance);
                    entity.setAccountId(account.getId());
                    entity.setBalance(newBalance);
                    return movementRepository.save(entity)
                            .flatMap(movements -> accountRepository.save(account));
                })
                .map(movement1 -> buildBodyMessage(movement1.getType().name().concat(" $").concat(String.valueOf(movement1.getInitialValue()))))
                .switchIfEmpty(Mono.error(new FundsUnavailable(NOT_FOUND)))
                .doOnSuccess(movementMono -> buildBodyMessage("Successfully transaction"))
                .doOnError(throwable -> log.error("Transaction error {}", throwable.getMessage()));
    }


    private MovementMessage buildBodyMessage(String message) {
        MovementMessage movementMessage = new MovementMessage();
        movementMessage.setMessage(message);
        return movementMessage;
    }


}
