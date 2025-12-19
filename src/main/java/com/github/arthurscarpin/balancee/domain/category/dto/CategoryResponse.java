package com.github.arthurscarpin.balancee.domain.category.dto;

import com.github.arthurscarpin.balancee.domain.category.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponse {

    private Long id;

    private String name;

    private CategoryType type;
}
