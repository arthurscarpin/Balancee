package com.github.arthurscarpin.balancee.domain.transaction.mapper;

import com.github.arthurscarpin.balancee.domain.category.model.Category;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionRequest;
import com.github.arthurscarpin.balancee.domain.transaction.dto.TransactionResponse;
import com.github.arthurscarpin.balancee.domain.transaction.model.Transaction;
import com.github.arthurscarpin.balancee.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction map(TransactionRequest transactionRequest, User user, Category category) {
        Transaction transaction = new Transaction();
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDate(transactionRequest.getDate());
        transaction.setUser(user);
        transaction.setCategory(category);
        return transaction;
    }

    public TransactionResponse map(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setDate(transaction.getDate());
        transactionResponse.setUserName(transaction.getUser().getName());
        transactionResponse.setCategoryType(transaction.getCategory().getType());
        return transactionResponse;
    }
}
