package com.github.arthurscarpin.balancee.domain.category;

public record CategoryResponseDTO(
        Long id,
        String name,
        CategoryType type
) {
}
