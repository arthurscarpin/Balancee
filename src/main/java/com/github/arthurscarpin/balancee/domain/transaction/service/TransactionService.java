package com.github.arthurscarpin.balancee.domain.transaction.service;

import com.github.arthurscarpin.balancee.domain.category.enums.CategoryType;
import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.category.repository.CategoryRepository;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionRequest;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionResponse;
import com.github.arthurscarpin.balancee.domain.transaction.mapper.TransactionMapper;
import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import com.github.arthurscarpin.balancee.domain.transaction.repository.TransactionRepository;
import com.github.arthurscarpin.balancee.domain.user.model.User;
import com.github.arthurscarpin.balancee.domain.user.repository.UserRepository;
import com.github.arthurscarpin.balancee.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final TransactionMapper mapper;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository, UserRepository userRepository, TransactionMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public TransactionResponse create(TransactionRequest transactionRequest) {
        User user = userRepository.findById(transactionRequest.getUserId())
                .orElseThrow(() -> new BusinessException("User not found!"));
        Category category = categoryRepository.findById(transactionRequest.getCategoryId())
                .orElseThrow(() -> new BusinessException("Category not found!"));
        if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Invalid amount! The amount must be greater than zero.");
        }
        Transaction transactionCreated = transactionRepository.save(mapper.map(transactionRequest, user, category));
        return mapper.map(transactionCreated);
    }

    public List<TransactionResponse> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public TransactionResponse findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Transaction not found!"));
        return mapper.map(transaction);
    }

    public List<TransactionResponse> findByUserId(Long id) {
        List<Transaction> transactions = transactionRepository.findByUserId(id);
        return transactions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> findByUserIdAndType(Long userId, CategoryType type) {
        boolean validType = EnumSet.of(CategoryType.INCOME, CategoryType.EXPENSE).contains(type);
        if (!validType) {
            throw new BusinessException("Category mismatch! Please a valid category \"INCOME\" and \"EXPENSE\"");
        }
        List<Transaction> transactions = transactionRepository.findByUserIdAndCategory_Type(userId, type);
        return transactions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public List<TransactionResponse> findByUserIdAndYearAndMonth(Long userId, CategoryType type, int year, int month) {
        if (!EnumSet.of(CategoryType.INCOME, CategoryType.EXPENSE).contains(type)) {
            throw new BusinessException("Category mismatch! Please a valid category \"INCOME\" and \"EXPENSE\"");
        }
        List<Transaction> transactions = transactionRepository.findByUserIdAndCategory_TypeAndYearAndMonth(userId, type, year, month);
        return transactions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public String calculateTotalExpense(Long id, int year, int month) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndExpenseTypeAndYearAndMonth(id, year, month);
        BigDecimal totalExpenses = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return "Total Expense: " + totalExpenses;
    }

    public String calculateTotalIncome(Long id, int year, int month) {
        List<Transaction> transactions = transactionRepository.findByUserIdAndIncomeTypeAndYearAndMonth(id, year, month);
        BigDecimal totalIncome = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return "Total Income: " + totalIncome;
    }

    public String calculateMonthlyBalance(Long id, int year, int month) {
        List<Transaction> transactionsExpense = transactionRepository.findByUserIdAndExpenseTypeAndYearAndMonth(id, year, month);
        BigDecimal totalExpense = transactionsExpense.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Transaction> transactionsIncome = transactionRepository.findByUserIdAndIncomeTypeAndYearAndMonth(id, year, month);
        BigDecimal totalIncome = transactionsIncome.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthlyBalance = totalIncome.subtract(totalExpense);
        return "Total Monthly Balance: " + monthlyBalance;
    }

    @Transactional
    public TransactionResponse updatePutById(Long id, TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Transaction not found!"));
        User user = userRepository.findById(transactionRequest.getUserId())
                .orElseThrow(() -> new BusinessException("User not found!"));
        Category category = categoryRepository.findById(transactionRequest.getCategoryId())
                .orElseThrow(() -> new BusinessException("Category not found!"));
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDate(transactionRequest.getDate());
        transaction.setUser(user);
        transaction.setCategory(category);
        return mapper.map(transactionRepository.save(transaction));
    }

    @Transactional
    public TransactionResponse updatePatchById(Long id, TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Transaction not found!"));
        if (transactionRequest.getDescription() != null) {
            transaction.setDescription(transactionRequest.getDescription());
        }
        if (transactionRequest.getAmount() != null) {
            transaction.setAmount(transactionRequest.getAmount());
        }
        if (transactionRequest.getDate() != null) {
            transaction.setDate(transactionRequest.getDate());
        }
        if (transactionRequest.getUserId() != null) {
            User user = userRepository.findById(transactionRequest.getUserId())
                    .orElseThrow(() -> new BusinessException("User not found!"));
            transaction.setUser(user);
        }
        if (transactionRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(transactionRequest.getCategoryId())
                    .orElseThrow(() -> new BusinessException("Category not found!"));
            transaction.setCategory(category);
        }
        return mapper.map(transactionRepository.save(transaction));
    }

    @Transactional
    public void deleteById(Long id) {
        if (transactionRepository.findById(id).isEmpty()) {
            throw new BusinessException("Transaction not found!");
        }
        transactionRepository.deleteById(id);
    }
}
