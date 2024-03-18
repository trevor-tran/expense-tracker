package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Category;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceTests {

    @Autowired
    private CategoryService categoryService;

    @ParameterizedTest
    @CsvSource({"foo", "bar"})
    void testSaveCategorySuccessful(String name) {
        categoryService.save(new Category(name));
        Optional<Category> category = categoryService.findByName(name);
        assertTrue(category.isPresent());
    }
}
