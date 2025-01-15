package com.cmaggessi.azbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity(name = "tb_account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private int agencyNumber = 1;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.valueOf(0.0);

    @Column(nullable = false)
    private String clientName;

}
