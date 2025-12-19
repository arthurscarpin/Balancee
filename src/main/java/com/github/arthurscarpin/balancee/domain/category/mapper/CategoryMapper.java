package com.github.arthurscarpin.balancee.domain.category.mapper;

import com.github.arthurscarpin.balancee.domain.category.dto.CategoryRequest;
import com.github.arthurscarpin.balancee.domain.category.dto.CategoryResponse;
import com.github.arthurscarpin.balancee.domain.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category map(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setType(categoryRequest.getType());
        return category;
    }

    public CategoryResponse map(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setType(category.getType());
        return categoryResponse;
    }
}
