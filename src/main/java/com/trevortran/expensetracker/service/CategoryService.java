package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Define available abstract methods for Category
 */
public interface CategoryService {
    /**
     * Persist a category
     * @param category
     */
    void save(Category category);

    /**
     * Delete the category by its id
     * @param id
     */
    void deleteById(long id);

    /**
     * find the category by its Id
     * @param id
     * @return category
     */
    Optional<Category> findById(long id);

    /**
     * Find the category by its name
     * @param name
     * @return found category
     */
    Optional<Category> findByName(String name);

    /**
     * Get all category items
     * @return list of category items
     */
    List<Category> findAll();
}
