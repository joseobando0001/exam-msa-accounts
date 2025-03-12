package com.pichincha.exam.accounts.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Error implements Serializable {
    private String message;
    private HttpStatus statusCode;

}
