package com.github.arthurscarpin.balancee.domain.category.dto;

import com.github.arthurscarpin.balancee.domain.category.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequest {

    @NotBlank
    private String name;

    @NotNull
    private CategoryType type;
}
