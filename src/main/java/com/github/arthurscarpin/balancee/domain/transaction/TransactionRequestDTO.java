package com.github.arthurscarpin.balancee.domain.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequestDTO(
        @NotBlank String description,
        @Positive @NotNull BigDecimal amount,
        @NotNull LocalDate date,
        @NotNull Long userId,
        @NotNull Long categoryId
) {
}
