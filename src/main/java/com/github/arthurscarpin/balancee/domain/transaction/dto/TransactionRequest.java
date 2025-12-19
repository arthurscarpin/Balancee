package com.github.arthurscarpin.balancee.domain.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionRequest {

    @NotBlank
    private String description;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    @NotNull
    private Long userId;

    @NotNull
    private Long categoryId;
}
