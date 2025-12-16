package com.github.arthurscarpin.balancee.domain.transaction.controller;

import com.github.arthurscarpin.balancee.domain.category.model.CategoryType;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionRequestDTO;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionResponseDTO;
import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import com.github.arthurscarpin.balancee.domain.transaction.service.TransactionService;
import com.github.arthurscarpin.balancee.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createPostTransaction(@RequestBody TransactionRequestDTO transaction) {
        try {
            TransactionResponseDTO transactionCreated = service.create(transaction);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(transactionCreated);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllGetTransaction() {
        List<TransactionResponseDTO> transactions = service.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdGetTransaction(@PathVariable Long id) {
        try {
            TransactionResponseDTO transaction = service.findById(id);
            return ResponseEntity.ok(transaction);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping(value = "/user/{userId}", params = {"!type", "!year", "!month"})
    public ResponseEntity<?> findByUserIdGetTransaction(@PathVariable Long userId) {
        try {
            List<TransactionResponseDTO> transaction = service.findByUserId(userId);
            return ResponseEntity.ok(transaction);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping(value = "/user/{userId}", params = "type")
    public ResponseEntity<?> findByUserIdAndTypeGetTransaction(
            @PathVariable Long userId,
            @RequestParam CategoryType type) {
        try {
            List<TransactionResponseDTO> transaction = service.findByUserIdAndType(userId, type);
            return ResponseEntity.ok(transaction);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping(value = "/user/{userId}", params = {"type", "year", "month"})
    public ResponseEntity<?> findByUserIdAndYearAndMonthGetTransaction(
            @PathVariable Long userId,
            @RequestParam CategoryType type,
            @RequestParam int year,
            @RequestParam int month) {
        try {
            List<TransactionResponseDTO> transaction = service.findByUserIdAndYearAndMonth(userId, type, year, month);
            return ResponseEntity.ok(transaction);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/total-expense/{userId}")
    public ResponseEntity<?> calculateTotalExpenseGetTransaction(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        String totalExpenses = service.calculateTotalExpense(userId, year, month);
        return ResponseEntity.ok(totalExpenses);
    }

    @GetMapping("/total-income/{userId}")
    public ResponseEntity<?> calculateTotalIncomeGetTransaction(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        String totalIncomes = service.calculateTotalIncome(userId, year, month);
        return ResponseEntity.ok(totalIncomes);
    }

    @GetMapping("/monthly-balance/{userId}")
    public ResponseEntity<?> calculateMonthlyBalanceGetTransaction(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        String monthlyBalance = service.calculateMonthlyBalance(userId, year, month);
        return ResponseEntity.ok(monthlyBalance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateByIdPutTransaction(@PathVariable Long id, @RequestBody TransactionRequestDTO transaction) {
        try {
            TransactionResponseDTO transactionUpdated = service.updateById(id, transaction);
            return ResponseEntity.ok(transactionUpdated);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByIdDeleteTransaction(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
