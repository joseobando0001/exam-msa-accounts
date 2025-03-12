package com.pichincha.exam.accounts.configuration;

import com.pichincha.exam.accounts.domain.dto.Error;
import com.pichincha.exam.accounts.exception.FundsUnavailable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

import static com.pichincha.exam.accounts.constants.ErrorConstants.BAD_VALUE;

@Slf4j
@RestControllerAdvice
public class ControllerHandler {

    @ExceptionHandler(FundsUnavailable.class)
    public ResponseEntity<Object> handleFailure(Exception exception) {
        log.error(exception.toString());
        return buildResponseEntity(new Error(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleFailureIllegal(Exception exception) {
        log.error(exception.toString());
        return buildResponseEntity(new Error(BAD_VALUE, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({ServerWebInputException.class})
    public ResponseEntity<Object> handleFailureInput(Exception exception) {
        log.error(exception.toString());
        return buildResponseEntity(new Error(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    private ResponseEntity<Object> buildResponseEntity(Error error) {
        return new ResponseEntity<>(error, error.getStatusCode());
    }
}
