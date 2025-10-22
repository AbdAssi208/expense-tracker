package com.abd.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeDto(
        Long id,
        BigDecimal amount,
        String source,
        LocalDate date
) {}
