package com.abd.expensetracker.controller;

import com.abd.expensetracker.dto.TransactionDto;
import com.abd.expensetracker.dto.UpsertTransactionRequest;
import com.abd.expensetracker.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions")
public class TransactionController {

    private final TransactionService service;

    private String requireEmail(Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return auth.getName();
    }

    @GetMapping
    @Operation(summary = "List all user's transactions")
    public List<TransactionDto> list(Authentication auth){
        return service.listForUser(requireEmail(auth));
    }

    @GetMapping("/between")
    @Operation(summary = "List transactions between two dates (inclusive)")
    public List<TransactionDto> listBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Authentication auth){
        return service.listBetweenForUser(requireEmail(auth), start, end);
    }

    @PostMapping
    @Operation(summary = "Create transaction")
    public TransactionDto create(@Valid @RequestBody UpsertTransactionRequest req, Authentication auth){
        return service.createForUser(requireEmail(auth), req);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by id")
    public TransactionDto get(@PathVariable("id") Long id, Authentication auth){
        return service.getForUser(requireEmail(auth), id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update transaction")
    public TransactionDto update(@PathVariable("id") Long id,
                                 @Valid @RequestBody UpsertTransactionRequest req,
                                 Authentication auth){
        return service.updateForUser(requireEmail(auth), id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete transaction")
    public void delete(@PathVariable("id") Long id, Authentication auth){
        service.deleteForUser(requireEmail(auth), id);
    }
}
