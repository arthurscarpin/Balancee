package com.github.arthurscarpin.balancee.domain.transaction;

import com.github.arthurscarpin.balancee.domain.category.Category;
import com.github.arthurscarpin.balancee.domain.user.User;
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
