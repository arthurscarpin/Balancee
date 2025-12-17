package com.github.arthurscarpin.balancee.domain.transaction.mapper;

import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionRequestDTO;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionResponseDTO;
import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import com.github.arthurscarpin.balancee.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction map(TransactionRequestDTO transactionDTO, User user, Category category) {
        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDTO.description());
        transaction.setAmount(transactionDTO.amount());
        transaction.setDate(transactionDTO.date());
        transaction.setUser(user);
        transaction.setCategory(category);
        return transaction;
    }

    public TransactionResponseDTO map(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getUser().getName(),
                transaction.getCategory().getType()
        );
    }
}
