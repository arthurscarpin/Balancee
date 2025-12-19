package com.github.arthurscarpin.balancee.domain.transaction.dto;

import com.github.arthurscarpin.balancee.domain.category.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResponse {

    private Long id;

    private String description;

    private BigDecimal amount;

    private LocalDate date;

    private String userName;

    private CategoryType categoryType;
}