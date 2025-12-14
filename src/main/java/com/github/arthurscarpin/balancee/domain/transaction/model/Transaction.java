package com.github.arthurscarpin.balancee.domain.transaction;

import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_transaction")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"user", "category"})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "description")
    private String description;

    @Positive
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
