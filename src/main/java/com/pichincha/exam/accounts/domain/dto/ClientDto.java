package com.pichincha.exam.accounts.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientDto implements Serializable {
    private Long id;
    private Long personId;
    private String password;
    private Boolean status;
}
