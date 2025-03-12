package com.pichincha.exam.accounts.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "Movement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movement implements Serializable {
    @Id
    private Long id;

    @Column("date")
    private LocalDateTime date;

    @Column("type")
    private String type;

    @Column("amount ")
    private BigDecimal amount;

    @Column("balance")
    private BigDecimal balance;

    @Column("account_id")
    private Account accountId;

}