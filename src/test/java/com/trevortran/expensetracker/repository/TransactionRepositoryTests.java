package com.trevortran.expensetracker.repository;

import com.trevortran.expensetracker.model.Category;
import com.trevortran.expensetracker.model.Transaction;
import com.trevortran.expensetracker.model.User;
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

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setupEach() {
        categoryRepository.save(new Category("gas"));
        categoryRepository.save(new Category("shopping"));
        User user = new User("test@gmail.com", "foo", "foo", "123", null);;
        userRepository.save(user);
    }

    @Test
    void testFindNonExistingTransactionsByUserId() {
        Optional<User> userOptional = userRepository.findByEmail("test@gmail.com");
        assertTrue(userOptional.isPresent());
        UUID userId = userOptional.get().getId();

        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void testFindExistingTransactionsByUserId() {
        Optional<User> userOptional = userRepository.findByEmail("test@gmail.com");
        assertTrue(userOptional.isPresent());
        UUID userId = userOptional.get().getId();

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
