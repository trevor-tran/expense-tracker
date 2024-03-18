package com.trevortran.expensetracker.repository;

import com.trevortran.expensetracker.model.Category;
import com.trevortran.expensetracker.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TransactionRepositoryTests {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setupEach() {
        categoryRepository.save(new Category("gas"));
        categoryRepository.save(new Category("shopping"));
    }

    @Test
    void testFindNonExistingTransactionsByUserId() {
        UUID userId = UUID.randomUUID();
        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void testFindExistingTransactionsByUserId() {
        UUID userId = UUID.randomUUID();

        Optional<Category> gasCategory = categoryRepository.findByName("gas");
        Optional<Category> shoppingCategory = categoryRepository.findByName("shopping");
        assertTrue(gasCategory.isPresent());
        assertTrue(shoppingCategory.isPresent());


        Transaction t1 = new Transaction(userId,
                LocalDate.now(),
                "foo1",
                1.0,
                gasCategory.get()
        );

        Transaction t2 = new Transaction(userId,
                LocalDate.now(),
                "foo2",
                2.0,
                shoppingCategory.get()
        );

        transactionRepository.save(t1);
        transactionRepository.save(t2);

        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
        assertEquals(2, transactions.size());
    }
}
