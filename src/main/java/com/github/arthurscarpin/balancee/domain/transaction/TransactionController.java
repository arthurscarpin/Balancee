package com.github.arthurscarpin.balancee.domain.transaction;

import com.github.arthurscarpin.balancee.domain.category.CategoryType;
import com.github.arthurscarpin.balancee.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create a new transaction.", description = "Creates a new transaction with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")
    })
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
    @Operation(summary = "List all transactions.", description = "Retrieves a list of all registered transactions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of transactions retrieved successfully."),
    })
    public ResponseEntity<?> findAllGetTransaction() {
        List<TransactionResponseDTO> transactions = service.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID.", description = "Retrieves a transaction by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction by ID."),
            @ApiResponse(responseCode = "404", description = "Transaction not found.")
    })
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
    @Operation(summary = "Get transaction by User ID.", description = "Retrieves a transaction by its unique User ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction by User ID."),
            @ApiResponse(responseCode = "404", description = "Transaction not found.")
    })
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
    @Operation(
            summary = "Get transaction by User User ID and Type.",
            description = "Retrieves a transaction by its unique User ID and Type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction by User ID and Type."),
            @ApiResponse(responseCode = "404", description = "Transaction not found.")
    })
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
    @Operation(
            summary = "Get transaction by User User ID, Type, Year and Month.",
            description = "Retrieves a transaction by its unique User ID, Type,Year and Month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction by User ID, Type, Year and Month."),
            @ApiResponse(responseCode = "404", description = "Transaction not found.")
    })
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
    @Operation(
            summary = "Report total Expense.",
            description = "Calculate the total balance of Expense.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value of Expense total amount."),
    })
    public ResponseEntity<?> calculateTotalExpenseGetTransaction(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        String totalExpenses = service.calculateTotalExpense(userId, year, month);
        return ResponseEntity.ok(totalExpenses);
    }

    @GetMapping("/total-income/{userId}")
    @Operation(
            summary = "Report total Income.",
            description = "Calculate the total balance of Income.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value of Income total amount."),
    })
    public ResponseEntity<?> calculateTotalIncomeGetTransaction(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        String totalIncomes = service.calculateTotalIncome(userId, year, month);
        return ResponseEntity.ok(totalIncomes);
    }

    @GetMapping("/monthly-balance/{userId}")
    @Operation(
            summary = "Report total montly banlance.",
            description = "Calculate the total montly banlance.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value of total montly banlance."),
    })
    public ResponseEntity<?> calculateMonthlyBalanceGetTransaction(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        String monthlyBalance = service.calculateMonthlyBalance(userId, year, month);
        return ResponseEntity.ok(monthlyBalance);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update transaction by ID.", description = "Updates the details of an existing transaction by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated successfully."),
            @ApiResponse(responseCode = "404", description = "Transaction not found.")
    })
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
    @Operation(summary = "Delete transaction by ID.", description = "Deletes a transaction by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Transaction not found.")
    })
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
