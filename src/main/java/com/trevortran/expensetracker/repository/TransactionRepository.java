package com.trevortran.expensetracker.repository;

import com.trevortran.expensetracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Transaction Repository responsible for Transaction Entity related operations
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId")
    List<Transaction> findAllByUserId(@Param("userId") UUID userId);
}
