package com.abd.expensetracker.controller;

import com.abd.expensetracker.dto.CreateIncomeRequest;
import com.abd.expensetracker.dto.IncomeDto;
import com.abd.expensetracker.service.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incomes")
public class IncomeController {
    private final IncomeService service;

    @Operation(summary = "Create income")
    @PostMapping public ResponseEntity<IncomeDto> create(@RequestBody CreateIncomeRequest req, Authentication auth){
        return ResponseEntity.ok(service.create(auth.getName(), req));
    }

    @Operation(summary = "List incomes for logged-in user")
    @GetMapping public ResponseEntity<List<IncomeDto>> list(Authentication auth){
        return ResponseEntity.ok(service.list(auth.getName()));
    }

    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable("id") Long id, Authentication auth){
        service.delete(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}

