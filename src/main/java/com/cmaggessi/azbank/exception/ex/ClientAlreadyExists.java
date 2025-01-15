package com.cmaggessi.azbank.exception.ex;

public class ClientAlreadyExists extends RuntimeException{
    public ClientAlreadyExists(String message) {
        super(message);
    }
}
