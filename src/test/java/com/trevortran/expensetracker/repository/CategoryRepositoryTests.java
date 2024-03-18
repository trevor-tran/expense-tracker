package com.trevortran.expensetracker.repository;

import com.trevortran.expensetracker.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testFindNonExistingCategoryByName() {
        Optional<Category> category = categoryRepository.findByName("foo");
        assertFalse(category.isPresent());
    }

    @Test
    void testFindExistingCategoryByName() {
        categoryRepository.save(new Category("foo"));
        Optional<Category> category = categoryRepository.findByName("foo");
        assertTrue(category.isPresent());
    }
}
