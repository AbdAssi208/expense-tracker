package com.abd.expensetracker.service;

import com.abd.expensetracker.dto.TransactionDto;
import com.abd.expensetracker.dto.UpsertTransactionRequest;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    List<TransactionDto> listForUser(String email);
    List<TransactionDto> listBetweenForUser(String email, LocalDate start, LocalDate end);
    TransactionDto createForUser(String email, UpsertTransactionRequest req);
    TransactionDto updateForUser(String email, Long id, UpsertTransactionRequest req);
    void deleteForUser(String email, Long id);
    TransactionDto getForUser(String email, Long id);
}
