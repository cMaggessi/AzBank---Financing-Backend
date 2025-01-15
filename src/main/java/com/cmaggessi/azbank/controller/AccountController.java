package com.cmaggessi.azbank.controller;

import com.cmaggessi.azbank.model.Account;
import com.cmaggessi.azbank.model.Transaction;
import com.cmaggessi.azbank.model.dto.AccountDTO;
import com.cmaggessi.azbank.model.dto.ValueDTO;
import com.cmaggessi.azbank.model.dto.TransferDTO;
import com.cmaggessi.azbank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Cria uma conta no AZBank.")
    @PostMapping("/create")
    public ResponseEntity<Account> create(@RequestBody AccountDTO dto) {
        Account acc = accountService.createAccount(dto);
        return new ResponseEntity<>(acc, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna o saldo de uma conta.")
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId) {
        BigDecimal balance = accountService.getBalance(accountId);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @Operation(summary = "Depositar um valor na conta.")
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long accountId, @RequestBody ValueDTO dto) {
        accountService.deposit(accountId, dto.getValue());
        return new ResponseEntity<>("Deposit successful.", HttpStatus.OK);
    }

    @Operation(summary = "Sacar um valor da conta.")
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long accountId, @RequestBody ValueDTO dto) {
        accountService.withdraw(accountId, dto.getValue());
        return new ResponseEntity<>("Withdraw successful.", HttpStatus.OK);
    }

    @Operation(summary = "Transferir um valor para uma conta.")
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO dto) {
        accountService.transfer(dto.getFrom(), dto.getTo(), dto.getValue());
        return new ResponseEntity<>("Transfer operation was successful.", HttpStatus.OK);
    }

    @Operation(summary = "Obter o extrato da conta.")
    @GetMapping("/{accountId}/extract")
    public ResponseEntity<List<Transaction>> getExtract(@PathVariable Long accountId) {
        List<Transaction> transc = accountService.getTransactions(accountId);
        return new ResponseEntity<>(transc, HttpStatus.OK);
    }

}
