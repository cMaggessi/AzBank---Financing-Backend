package com.cmaggessi.azbank.model.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransferDTO {

    private Long from;
    private Long to;
    private BigDecimal value;
}
