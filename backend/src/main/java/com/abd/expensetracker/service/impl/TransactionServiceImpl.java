package com.abd.expensetracker.service.impl;

import com.abd.expensetracker.dto.TransactionDto;
import com.abd.expensetracker.dto.UpsertTransactionRequest;
import com.abd.expensetracker.model.Category;
import com.abd.expensetracker.model.Transaction;
import com.abd.expensetracker.model.User;
import com.abd.expensetracker.repository.CategoryRepository;
import com.abd.expensetracker.repository.TransactionRepository;
import com.abd.expensetracker.repository.UserRepository;
import com.abd.expensetracker.service.TransactionService;
import com.abd.expensetracker.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository txRepo;
    private final CategoryRepository catRepo;
    private final TransactionMapper mapper;

    private User userByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    @Override
    public List<TransactionDto> listForUser(String email) {
        User u = userByEmail(email);
        return txRepo.findByOwner(u).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<TransactionDto> listBetweenForUser(String email, LocalDate start, LocalDate end) {
        User u = userByEmail(email);
        return txRepo.findByOwnerAndDateBetween(u, start, end).stream().map(mapper::toDto).toList();
    }

    @Override
    public TransactionDto createForUser(String email, UpsertTransactionRequest req) {
        User u = userByEmail(email);
        Transaction t = new Transaction();
        t.setOwner(u);
        // الواجهة ترسل title: نخزّنه في الوصف
        t.setDescription(req.getTitle());
        t.setAmount(req.getAmount());
        t.setDate(req.getDate());
        // لازم type غير null
        if (t.getType() == null) t.setType(com.abd.expensetracker.model.enums.TransactionType.EXPENSE);

        if (req.getCategoryId() != null) {
            Category c = catRepo.findByIdAndOwner(req.getCategoryId(), u)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));
            t.setCategory(c);
        }

        return mapper.toDto(txRepo.save(t));
    }

    @Override
    public TransactionDto updateForUser(String email, Long id, UpsertTransactionRequest req) {
        User u = userByEmail(email);
        Transaction t = txRepo.findByIdAndOwner(id, u)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        t.setDescription(req.getTitle());
        t.setAmount(req.getAmount());
        t.setDate(req.getDate());
        if (t.getType() == null) t.setType(com.abd.expensetracker.model.enums.TransactionType.EXPENSE);

        if (req.getCategoryId() != null) {
            Category c = catRepo.findByIdAndOwner(req.getCategoryId(), u)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));
            t.setCategory(c);
        } else {
            t.setCategory(null);
        }

        return mapper.toDto(txRepo.save(t));
    }

    @Override
    public void deleteForUser(String email, Long id) {
        User u = userByEmail(email);
        Transaction t = txRepo.findByIdAndOwner(id, u)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
        txRepo.delete(t);
    }

    @Override
    public TransactionDto getForUser(String email, Long id) {
        User u = userByEmail(email);
        Transaction t = txRepo.findByIdAndOwner(id, u)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
        return mapper.toDto(t);
    }
}
