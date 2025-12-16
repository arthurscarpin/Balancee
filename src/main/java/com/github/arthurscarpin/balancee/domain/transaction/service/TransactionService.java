package com.github.arthurscarpin.balancee.domain.transaction.service;

import com.github.arthurscarpin.balancee.domain.category.model.CategoryType;
import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import com.github.arthurscarpin.balancee.domain.transaction.repository.TransactionRepository;
import com.github.arthurscarpin.balancee.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            return repository.save(transaction);
        } else {
            throw new BusinessException("Invalid amount! The amount must be greater than zero.");
        }
    }

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction findById(Long id) {
        Optional<Transaction> transactionExists = repository.findById(id);
        return transactionExists.orElseThrow(() -> new BusinessException("Transaction not found!"));
    }

    public List<Transaction> findByUserId(Long id) {
        Optional<List<Transaction>> transactionExists = repository.findByUserId(id);
        return transactionExists.orElseThrow(() -> new BusinessException("Transaction not found!"));
    }

    public List<Transaction> findByUserIdAndType(Long userId, CategoryType type) {
        if (!type.equals(CategoryType.INCOME) && !type.equals(CategoryType.EXPENSE)) {
            throw new BusinessException("Category mismatch! Please a valid category \"INCOME\" and \"EXPENSE\"");
        } else {
            Optional<List<Transaction>> transactionExists = repository.findByType(userId, type);
            return transactionExists.orElseThrow(() -> new BusinessException("Transaction not found!"));
        }
    }

    public List<Transaction> findByUserIdAndYearAndMonth(Long id, CategoryType type, int year, int month) {
        if (!type.equals(CategoryType.INCOME) && !type.equals(CategoryType.EXPENSE)) {
            throw new BusinessException("Category mismatch! Please a valid category \"INCOME\" and \"EXPENSE\"");
        } else {
            Optional<List<Transaction>> transactionExists = repository.findByTypeYearAndMonth(id, type, year, month);
            return transactionExists.orElseThrow(() -> new BusinessException("Transaction not found!"));
        }
    }

    public String calculateTotalExpense(Long id, int year, int month) {
        List<Transaction> transactions = repository.findByTypeExpenseYearAndMonth(id, year, month);
        BigDecimal totalExpenses = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return "Total Expense: " + totalExpenses;
    }

    public String calculateTotalIncome(Long id, int year, int month) {
        List<Transaction> transactions = repository.findByTypeIncomeYearAndMonth(id, year, month);
        BigDecimal totalIncome = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return "Total Income: " + totalIncome;
    }

    public String calculateMonthlyBalance(Long id, int year, int month) {
        List<Transaction> transactionsExpense = repository.findByTypeExpenseYearAndMonth(id, year, month);
        BigDecimal totalExpense = transactionsExpense.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Transaction> transactionsIncome = repository.findByTypeIncomeYearAndMonth(id, year, month);
        BigDecimal totalIncome = transactionsIncome.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthlyBalance = totalIncome.subtract(totalExpense);
        return "Total Monthly Balance: " + monthlyBalance;
    }

    @Transactional
    public Transaction updateById(Long id, Transaction transactionUpdate) {
        Optional<Transaction> transactionExists = repository.findById(id);
        if (transactionExists.isPresent()) {
            transactionUpdate.setId(id);
            return repository.save(transactionUpdate);
        } else {
            throw new BusinessException("Transaction not found!");
        }
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Transaction> transactionExists = repository.findById(id);
        if (transactionExists.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new BusinessException("Transaction not found!");
        }
    }
}
