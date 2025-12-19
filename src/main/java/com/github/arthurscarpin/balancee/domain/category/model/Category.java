package com.github.arthurscarpin.balancee.domain.category.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.arthurscarpin.balancee.domain.category.enums.CategoryType;
import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "tb_category")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CategoryType type;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;
}
