package com.github.arthurscarpin.balancee.domain.category.service;

import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.category.model.CategoryType;
import com.github.arthurscarpin.balancee.domain.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Category create(Category category) {
        if (category.getType() != CategoryType.INCOME && category.getType() != CategoryType.EXPENSE) {
            return null;
        } else {
            return repository.save(category);
        }
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        Optional<Category> categoryExists = repository.findById(id);
        return categoryExists.orElse(null);
    }

    @Transactional
    public Category updateById(Long id, Category categoryUpdate) {
        Optional<Category> categoryExists = repository.findById(id);
        if (categoryExists.isPresent()) {
            categoryUpdate.setId(id);
            return repository.save(categoryUpdate);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
