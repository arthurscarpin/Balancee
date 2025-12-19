package com.github.arthurscarpin.balancee.domain.transaction.repository;

import com.github.arthurscarpin.balancee.domain.category.enums.CategoryType;
import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndCategory_Type(
            Long userId,
            CategoryType type
    );

    @Query(value = """
            SELECT t
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.category.type = :type
            AND YEAR(t.date) = :year AND MONTH(t.date) = :month
            """)
    List<Transaction> findByUserIdAndCategory_TypeAndYearAndMonth(
            @Param("userId") Long userId,
            @Param("type") CategoryType type,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query(value = """
            SELECT t
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.category.type = 'EXPENSE'
            AND YEAR(t.date) = :year AND MONTH(t.date) = :month""")
    List<Transaction> findByUserIdAndExpenseTypeAndYearAndMonth(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query(value = """
            SELECT t
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.category.type = 'INCOME'
            AND YEAR(t.date) = :year AND MONTH(t.date) = :month""")
    List<Transaction> findByUserIdAndIncomeTypeAndYearAndMonth(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month
    );
}
