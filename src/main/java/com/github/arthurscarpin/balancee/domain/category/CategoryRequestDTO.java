package com.github.arthurscarpin.balancee.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(
        @NotBlank String name,
        @NotNull CategoryType type
) {
}
