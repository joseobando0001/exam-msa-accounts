package com.pichincha.exam.accounts.exception;

public class DuplicateAccount extends RuntimeException {

    public DuplicateAccount(String message) {
        super(message);
    }
}
