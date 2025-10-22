package com.abd.expensetracker.dto;

import com.abd.expensetracker.model.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionDto {
    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDate date;
    private String note;
    private String description;
    private Long categoryId;
    private String categoryName;
}

