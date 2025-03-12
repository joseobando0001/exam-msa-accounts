package com.pichincha.exam.accounts.exception;

public class FundsUnavailable extends RuntimeException {

    public FundsUnavailable(String message) {
        super(message);
    }
}
