package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.Category;
import com.trevortran.expensetracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void saveC(Category category) {
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(long id) {
       return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
