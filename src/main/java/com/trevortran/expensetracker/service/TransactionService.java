package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Transaction;

import java.util.UUID;

public interface TransactionService {
    void save(Transaction transaction);
    void delete(UUID id);
    void update(Transaction transaction);
    boolean existsById(UUID id);
}
