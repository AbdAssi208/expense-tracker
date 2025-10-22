package com.abd.expensetracker.repository;

import com.abd.expensetracker.model.Transaction;
import com.abd.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOwner(User owner);
    Optional<Transaction> findByIdAndOwner(Long id, User owner);
    List<Transaction> findByOwnerAndDateBetween(User owner, LocalDate start, LocalDate end);
}

