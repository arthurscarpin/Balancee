package com.github.arthurscarpin.balancee.domain.category.service;

import com.github.arthurscarpin.balancee.domain.category.dto.CategoryRequestDTO;
import com.github.arthurscarpin.balancee.domain.category.dto.CategoryResponseDTO;
import com.github.arthurscarpin.balancee.domain.category.mapper.CategoryMapper;
import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.category.model.CategoryType;
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
    public CategoryResponseDTO create(CategoryRequestDTO categoryDTO) {
        if (!EnumSet.of(CategoryType.INCOME, CategoryType.EXPENSE).contains(categoryDTO.type())) {
            throw new BusinessException("Invalid type!");
        }
        Category categoryCreated = repository.save(mapper.map(categoryDTO));
        return mapper.map(categoryCreated);
    }

    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = repository.findAll();
        return categories.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found!"));
        return mapper.map(category);
    }

    @Transactional
    public CategoryResponseDTO updateById(Long id, CategoryRequestDTO categoryDTO) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Category not found!"));
        category.setName(categoryDTO.name());
        category.setType(categoryDTO.type());
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
