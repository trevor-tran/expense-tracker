package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Transaction;
import com.trevortran.expensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return transactionRepository.existsById(id);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
       return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllByUserId(UUID userId) {
        return transactionRepository.findAllByUserId(userId);
    }
}
