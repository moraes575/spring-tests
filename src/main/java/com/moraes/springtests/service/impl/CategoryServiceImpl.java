package com.moraes.springtests.service.impl;

import com.moraes.springtests.model.Category;
import com.moraes.springtests.repository.CategoryRepository;
import com.moraes.springtests.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found. ID: " + id));
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Category update(Category category, Long id) {
        Category oldCategory = findById(id);
        if (oldCategory.equals(category)) {
            return repository.save(category);
        }
        throw new RuntimeException("Can't update different entities");
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
