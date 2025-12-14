package com.github.arthurscarpin.balancee.domain.transaction.repository;

import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
