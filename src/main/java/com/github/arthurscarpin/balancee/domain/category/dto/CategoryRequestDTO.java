package com.github.arthurscarpin.balancee.domain.category.dto;

import com.github.arthurscarpin.balancee.domain.category.model.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
        Long id,
        @NotBlank String name,
        @NotNull CategoryType type
) {
}
