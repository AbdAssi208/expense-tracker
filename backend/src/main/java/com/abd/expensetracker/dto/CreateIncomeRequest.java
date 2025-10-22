package com.abd.expensetracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateIncomeRequest(
        @NotNull @Positive BigDecimal amount,
        String source,
        LocalDate date
) {}

