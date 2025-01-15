package com.cmaggessi.azbank.service;

import com.cmaggessi.azbank.enums.TransactionType;
import com.cmaggessi.azbank.model.Account;
import com.cmaggessi.azbank.model.Transaction;
import com.cmaggessi.azbank.model.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account createAccount(AccountDTO dto);

    BigDecimal getBalance(Long accountId);

    void deposit(Long accountId, BigDecimal value);

    void withdraw(Long accountId, BigDecimal value);

    void transfer(Long fromAcc, Long toAcc, BigDecimal value);

    void saveTransaction(Account acc, TransactionType type, BigDecimal value, String description);

    List<Transaction> getTransactions(Long accountId);
}
