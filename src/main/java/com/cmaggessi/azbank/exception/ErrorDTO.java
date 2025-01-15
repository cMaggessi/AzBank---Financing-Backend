package com.cmaggessi.azbank.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

    private int code;
    private String message;

    public ErrorDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
