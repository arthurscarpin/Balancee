package com.github.arthurscarpin.balancee.domain.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
}
