package com.cmaggessi.azbank.model;

import com.cmaggessi.azbank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "tb_transaction")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // DEPOSIT, WITHDRAW, TRANSFER

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    private Account account;

}
