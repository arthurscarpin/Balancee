package com.github.arthurscarpin.balancee.domain.category.service;

import com.github.arthurscarpin.balancee.domain.category.dto.CategoryRequest;
import com.github.arthurscarpin.balancee.domain.category.dto.CategoryResponse;
import com.github.arthurscarpin.balancee.domain.category.enums.CategoryType;
import com.github.arthurscarpin.balancee.domain.category.mapper.CategoryMapper;
import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.category.repository.CategoryRepository;
import com.github.arthurscarpin.balancee.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public CategoryResponse create(CategoryRequest categoryRequest) {
        if (!EnumSet.of(CategoryType.INCOME, CategoryType.EXPENSE).contains(categoryRequest.getType())) {
            throw new BusinessException("Invalid type!");
        }
        Category categoryCreated = repository.save(mapper.map(categoryRequest));
        return mapper.map(categoryCreated);
    }

    public List<CategoryResponse> findAll() {
        List<Category> categories = repository.findAll();
        return categories.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found!"));
        return mapper.map(category);
    }

    @Transactional
    public CategoryResponse updatePutById(Long id, CategoryRequest categoryRequest) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found!"));
        category.setName(categoryRequest.getName());
        category.setType(categoryRequest.getType());
        return mapper.map(repository.save(category));
    }

    @Transactional
    public CategoryResponse updatePatchById(Long id, CategoryRequest categoryRequest) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found!"));
        if (categoryRequest.getName() != null) {
            category.setName(categoryRequest.getName());
        }
        if (categoryRequest.getType() != null) {
            category.setType(categoryRequest.getType());
        }
        return mapper.map(repository.save(category));
    }

    @Transactional
    public void deleteById(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new BusinessException("Category not found!");
        }
        repository.deleteById(id);
    }
}
