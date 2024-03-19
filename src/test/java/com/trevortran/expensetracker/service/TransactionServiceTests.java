package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Category;
import com.trevortran.expensetracker.model.Transaction;
import com.trevortran.expensetracker.model.UserCreationDTO;
import com.trevortran.expensetracker.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceTests {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        categoryService.save(new Category("gas"));
        UserCreationDTO user = new UserCreationDTO("foo", "foo", "bar@bar.com", "123", "123");
        userService.create(user);
    }

    @Test
    void testSaveTransactionSuccessfully() {
        Optional<Category> gasCategory = categoryService.findByName("gas");
        assertTrue(gasCategory.isPresent());

        UserPrincipal userPrincipal = (UserPrincipal)userService.loadUserByUsername("bar@bar.com");

        Transaction t = new Transaction(userPrincipal.getId(),
                LocalDate.now(),
                "foo1",
                1.0,
                gasCategory.get()
        );

        Transaction persistedT = transactionService.save(t);

        assertEquals(t, persistedT);
    }
}
