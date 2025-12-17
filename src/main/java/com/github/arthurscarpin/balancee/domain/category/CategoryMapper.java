package com.github.arthurscarpin.balancee.domain.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category map(CategoryRequestDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.name());
        category.setType(categoryDTO.type());
        return category;
    }

    public CategoryResponseDTO map(Category user) {
        return new CategoryResponseDTO(
                user.getId(),
                user.getName(),
                user.getType()
        );
    }
}
