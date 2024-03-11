package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Transaction save(Transaction transaction);
    void delete(UUID id);
    void update(Transaction transaction);
    boolean existsById(UUID id);

    List<Transaction> findAll();
}
