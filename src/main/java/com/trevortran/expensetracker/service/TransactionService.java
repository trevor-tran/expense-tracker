package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define available abstract methods for Transaction
 */
public interface TransactionService {

    /**
     * Persist a transaction
     * @param transaction the Transaction wants to be persisted
     * @return persisted transaction
     */
    Transaction save(Transaction transaction);

    /**
     * Delete transaction by its Id
     * @param id id of the Transaction
     */
    void delete(UUID id);

    /**
     * Check if a Transaction already exists
     * @param id id of the Transaction
     * @return true if exists, false otherwise
     */
    boolean existsById(UUID id);

    /**
     * Find transaction by its id
     * @param id id of the transaction
     * @return found transaction
     */
    Optional<Transaction> findById(UUID id);

    /**
     * find all Transaction items belong to a user
     * @param uuid id of the user
     * @return list of Transaction items
     */
    List<Transaction> findAllByUserId(UUID uuid);
}
