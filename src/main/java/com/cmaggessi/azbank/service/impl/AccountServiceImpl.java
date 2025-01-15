package com.cmaggessi.azbank.service.impl;

import com.cmaggessi.azbank.enums.TransactionType;
import com.cmaggessi.azbank.exception.ex.AccountNotFound;
import com.cmaggessi.azbank.exception.ex.ClientAlreadyExists;
import com.cmaggessi.azbank.exception.ex.InsufficientFundsException;
import com.cmaggessi.azbank.exception.ex.InvalidOperation;
import com.cmaggessi.azbank.model.Account;
import com.cmaggessi.azbank.model.Transaction;
import com.cmaggessi.azbank.model.dto.AccountDTO;
import com.cmaggessi.azbank.repository.AccountRepository;
import com.cmaggessi.azbank.repository.TransactionRepository;
import com.cmaggessi.azbank.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(AccountDTO dto) {
        if(checkClientExists(dto.getClientName())) {
            throw new ClientAlreadyExists("Client ("+dto.getClientName()+") already exists");
        }
        Account acc = new Account();
        acc.setClientName(dto.getClientName());
        acc.setAccountNumber(generateAccountNumber());
        return accountRepository.save(acc);
    }

    @Override
    public BigDecimal getBalance(Long accountId) {
        Account acc = getAccount(accountId);
        return acc.getBalance();
    }

    @Override
    public void deposit(Long accountId, BigDecimal value) {
        Account acc = getAccount(accountId);
        validateValue(value);
        acc.setBalance(acc.getBalance().add(value));
        accountRepository.save(acc);
        saveTransaction(acc, TransactionType.DEPOSIT, value, formatText("Deposited $%.2f USD.", value));
    }



    @Override
    public void withdraw(Long accountId, BigDecimal value) {
        Account acc = getAccount(accountId);
        validateValue(value);
        if(acc.getBalance().compareTo(value) < 0) throw new InsufficientFundsException("Insufficient funds.");
        acc.setBalance(acc.getBalance().subtract(value));
        accountRepository.save(acc);
        saveTransaction(acc, TransactionType.WITHDRAW, value, formatText("Withdrawn $%.2f USD.", value));
    }

    @Override
    public void transfer(Long fromAcc, Long toAcc, BigDecimal value) {
        Account from = getAccount(fromAcc);
        Account toAccount = getAccount(toAcc);

        // validations
        if(from.equals(toAccount)) throw new InvalidOperation("Invalid operation: From and to account is the same.");
        if(from.getBalance().compareTo(value) < 0) throw new InsufficientFundsException("Insufficient funds.");
        validateValue(value);

        from.setBalance(from.getBalance().subtract(value));
        toAccount.setBalance(toAccount.getBalance().add(value));

        accountRepository.save(from);
        accountRepository.save(toAccount);

        saveTransaction(
                from,
                TransactionType.TRANSFER,
                value,
                formatText("Transferred $%.2f USD to account: "+from.getAccountNumber()+".",value)
        );

    }

    @Override
    public void saveTransaction(Account acc, TransactionType type, BigDecimal value, String description) {
        Transaction transc = new Transaction();
        transc.setAccount(acc);
        transc.setTransactionType(type);
        transc.setValue(value);
        transc.setDescription(description);
        transc.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transc);
    }

    @Override
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }


    // private methods

    private String generateAccountNumber() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFound("Account with id " + accountId + " not found."));
    }

    private boolean checkClientExists(String clientName) {
        return accountRepository.findByClientName(clientName).isPresent();
    }

    private String formatText(String message, BigDecimal value) {
        return String.format(message, value);
    }

    private static void validateValue(BigDecimal value) {
        if(value.compareTo(BigDecimal.ZERO) == 0) throw new InvalidOperation("Value cannot be 0.");
    }

}
