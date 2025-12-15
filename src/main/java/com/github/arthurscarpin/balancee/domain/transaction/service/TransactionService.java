package com.github.arthurscarpin.balancee.domain.transaction.service;

import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import com.github.arthurscarpin.balancee.domain.transaction.model.TransactionType;
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
            if (transaction.getType() != TransactionType.INCOME && transaction.getType() != TransactionType.EXPENSE) {
                throw new BusinessException("Invalid type!");
            } else {
                return repository.save(transaction);
            }
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

    public List<Transaction> findByType(Long id, Transaction transaction) {
        if (transaction.getType() != TransactionType.INCOME && transaction.getType() != TransactionType.EXPENSE) {
            throw new BusinessException("Category mismatch! Please a valid category \"INCOME\" and \"EXPENSE\"");
        } else {
            Optional<List<Transaction>> transactionExists = repository.findByType(id, transaction.getType().name());
            return transactionExists.orElseThrow(() -> new BusinessException("Transaction not found!"));
        }
    }

    public List<Transaction> findByYearAndMonth(Long id, int month, int year) {
        Optional<List<Transaction>> transactionExists = repository.findByYearAndMonth(id, year, month);
        return transactionExists.orElseThrow(() -> new BusinessException("Transaction not found!"));
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
        repository.deleteById(id);
    }
}
