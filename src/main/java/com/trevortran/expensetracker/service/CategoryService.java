package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Category;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CategoryService {
    void saveCategory(Category category);
    void deleteCategoryById(long id);
    Optional<Category> findCategoryById(long id);
}
