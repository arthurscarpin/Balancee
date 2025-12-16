package com.github.arthurscarpin.balancee.domain.category.dto;

import com.github.arthurscarpin.balancee.domain.category.model.CategoryType;

public record CategoryResponseDTO(
        Long id,
        String name,
        CategoryType type
) {
}
