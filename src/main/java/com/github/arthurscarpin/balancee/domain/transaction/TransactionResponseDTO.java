package com.github.arthurscarpin.balancee.domain.transaction;

import com.github.arthurscarpin.balancee.domain.category.CategoryType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponseDTO(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate date,
        String userName,
        CategoryType categoryType
) {
}
