package com.cmaggessi.azbank.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAW("withdrawe"),
    TRANSFER("transfer");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }


}
