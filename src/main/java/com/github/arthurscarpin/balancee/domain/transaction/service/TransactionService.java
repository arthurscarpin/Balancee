package com.github.arthurscarpin.balancee.domain.transaction.service;

import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.category.model.CategoryType;
import com.github.arthurscarpin.balancee.domain.category.repository.CategoryRepository;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionRequestDTO;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionResponseDTO;
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
    public TransactionResponseDTO create(TransactionRequestDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.userId())
                .orElseThrow(() -> new BusinessException("User not found!"));
        Category category = categoryRepository.findById(transactionDTO.categoryId())
                .orElseThrow(() -> new BusinessException("Category not found!"));
        if (transactionDTO.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Invalid amount! The amount must be greater than zero.");
        }
        Transaction transactionCreated = transactionRepository.save(mapper.map(transactionDTO, user, category));
        return mapper.map(transactionCreated);
    }

    public List<TransactionResponseDTO> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public TransactionResponseDTO findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Transaction not found!"));
        return mapper.map(transaction);
    }

    public List<TransactionResponseDTO> findByUserId(Long id) {
        List<Transaction> transactions = transactionRepository.findByUserId(id);
        return transactions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> findByUserIdAndType(Long userId, CategoryType type) {
        boolean validType = EnumSet.of(CategoryType.INCOME, CategoryType.EXPENSE).contains(type);
        if (!validType) {
            throw new BusinessException("Category mismatch! Please a valid category \"INCOME\" and \"EXPENSE\"");
        }
        List<Transaction> transactions = transactionRepository.findByType(userId, type);
        return transactions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> findByUserIdAndYearAndMonth(Long userId, CategoryType type, int year, int month) {
        if (!EnumSet.of(CategoryType.INCOME, CategoryType.EXPENSE).contains(type)) {
            throw new BusinessException("Category mismatch! Please a valid category \"INCOME\" and \"EXPENSE\"");
        }
        List<Transaction> transactions = transactionRepository.findByTypeYearAndMonth(userId, type, year, month);
        return transactions.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public String calculateTotalExpense(Long id, int year, int month) {
        List<Transaction> transactions = transactionRepository.findByTypeExpenseYearAndMonth(id, year, month);
        BigDecimal totalExpenses = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return "Total Expense: " + totalExpenses;
    }

    public String calculateTotalIncome(Long id, int year, int month) {
        List<Transaction> transactions = transactionRepository.findByTypeIncomeYearAndMonth(id, year, month);
        BigDecimal totalIncome = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return "Total Income: " + totalIncome;
    }

    public String calculateMonthlyBalance(Long id, int year, int month) {
        List<Transaction> transactionsExpense = transactionRepository.findByTypeExpenseYearAndMonth(id, year, month);
        BigDecimal totalExpense = transactionsExpense.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Transaction> transactionsIncome = transactionRepository.findByTypeIncomeYearAndMonth(id, year, month);
        BigDecimal totalIncome = transactionsIncome.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthlyBalance = totalIncome.subtract(totalExpense);
        return "Total Monthly Balance: " + monthlyBalance;
    }

    @Transactional
    public TransactionResponseDTO updateById(Long id, TransactionRequestDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Transaction not found!"));
        User user = userRepository.findById(transactionDTO.userId())
                .orElseThrow(() -> new BusinessException("User not found!"));
        Category category = categoryRepository.findById(transactionDTO.categoryId())
                .orElseThrow(() -> new BusinessException("Category not found!"));
        transaction.setDescription(transactionDTO.description());
        transaction.setAmount(transactionDTO.amount());
        transaction.setDate(transactionDTO.date());
        transaction.setUser(user);
        transaction.setCategory(category);
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
