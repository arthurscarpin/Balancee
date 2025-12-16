package com.github.arthurscarpin.balancee.domain.transaction.controller;

import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import com.github.arthurscarpin.balancee.domain.transaction.model.TransactionType;
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
    public ResponseEntity<?> createPostTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction transactionCreated = service.create(transaction);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(transactionCreated);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllGetTransaction() {
        List<Transaction> transactions = service.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdGetTransaction(@PathVariable Long id) {
        try {
            Transaction transaction = service.findById(id);
            return ResponseEntity.ok(transaction);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByUserIdGetTransaction(@PathVariable Long userId) {
        try {
            List<Transaction> transaction = service.findByUserId(userId);
            return ResponseEntity.ok(transaction);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/users")
    public ResponseEntity<?> findByUserIdAndTypeGetTransaction(
            @PathVariable Long userId,
            @RequestParam TransactionType type) {
        try {
            List<Transaction> transaction = service.findByUserIdAndType(userId, type);
            return ResponseEntity.ok(transaction);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/users/date")
    public ResponseEntity<?> findByUserIdAndYearAndMonthGetTransaction(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        try {
            List<Transaction> transaction = service.findByUserIdAndYearAndMonth(userId, year, month);
            return ResponseEntity.ok(transaction);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateByIdPutTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        try {
            Transaction transactionUpdated = service.updateById(id, transaction);
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
