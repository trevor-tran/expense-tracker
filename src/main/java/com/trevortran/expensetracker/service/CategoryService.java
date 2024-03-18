package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void save(Category category);
    void deleteById(long id);
    Optional<Category> findById(long id);
    Optional<Category> findByName(String name);
    List<Category> findAll();
}
