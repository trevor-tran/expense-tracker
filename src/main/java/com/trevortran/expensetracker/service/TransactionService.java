package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Transaction;

import java.util.UUID;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
    void deleteTransaction(UUID id);

}
