package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    Transaction save(Transaction transaction);
    void delete(UUID id);
    boolean existsById(UUID id);
    Optional<Transaction> findById(UUID id);
    List<Transaction> findAll();
}
