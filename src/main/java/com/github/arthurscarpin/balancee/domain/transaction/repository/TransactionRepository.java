package com.github.arthurscarpin.balancee.domain.transaction.repository;

import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT * FROM Transaction t WHERE t.user.id = :userId")
    List<Transaction> findByUserId(@Param("userId") Long userId);

    @Query ("SELECT * FROM Transaction t WHERE t.user.id = :userId AND t.type = :type")
    List<Transaction> findByType(@Param("userId") Long userId, @Param("type") String type);

    @Query("SELECT * FROM Transaction t WHERE t.user.id = :userId AND YEAR(t.date) = :year AND MONTH(t.date) = :month")
    List<Transaction> findByYearAndMonth(@Param("userId") Long userId, @Param("year") int year, @Param("month") int month);
}
