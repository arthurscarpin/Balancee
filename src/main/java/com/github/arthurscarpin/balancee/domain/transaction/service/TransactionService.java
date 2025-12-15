package com.github.arthurscarpin.balancee.domain.transaction.service;

import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import com.github.arthurscarpin.balancee.domain.transaction.model.TransactionType;
import com.github.arthurscarpin.balancee.domain.transaction.repository.TransactionRepository;
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
                return null;
            } else {
                return repository.save(transaction);
            }
        } else {
            return null;
        }
    }

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction findById(Long id) {
        Optional<Transaction> transactionExists = repository.findById(id);
        return transactionExists.orElse(null);
    }

    public List<Transaction> findByUserId(Long id) {
        Optional<List<Transaction>> transactionExists = repository.findByUserId(id);
        return transactionExists.orElse(null);
    }

    public List<Transaction> findByType(Long id, String type) {
        Optional<List<Transaction>> transactionExists = repository.findByType(id, type);
        return transactionExists.orElse(null);
    }

    public List<Transaction> findByType(Long id, int month, int year) {
        Optional<List<Transaction>> transactionExists = repository.findByYearAndMonth(id, year, month);
        return transactionExists.orElse(null);
    }

    @Transactional
    public Transaction updateById(Long id, Transaction transactionUpdate) {
        Optional<Transaction> transactionExists = repository.findById(id);
        if (transactionExists.isPresent()) {
            transactionUpdate.setId(id);
            return repository.save(transactionUpdate);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
