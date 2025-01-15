package com.cmaggessi.azbank.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransactionDTO {
    private String description;
    private LocalDateTime timestamp;
}
