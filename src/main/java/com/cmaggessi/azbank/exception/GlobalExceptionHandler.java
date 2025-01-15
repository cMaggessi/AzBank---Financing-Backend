package com.cmaggessi.azbank.exception;

import com.cmaggessi.azbank.exception.ex.AccountNotFound;
import com.cmaggessi.azbank.exception.ex.ClientAlreadyExists;
import com.cmaggessi.azbank.exception.ex.InsufficientFundsException;
import com.cmaggessi.azbank.exception.ex.InvalidOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorDTO> accountNotFound(AccountNotFound ex) {
        ErrorDTO dto = new ErrorDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorDTO> insufficentFounds(InsufficientFundsException ex) {
        ErrorDTO dto = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientAlreadyExists.class)
    public ResponseEntity<ErrorDTO> clientAlreadyExists(ClientAlreadyExists ex) {
        ErrorDTO dto = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOperation.class)
    public ResponseEntity<ErrorDTO> invalidOperation(InvalidOperation ex) {
        ErrorDTO dto = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }
}
