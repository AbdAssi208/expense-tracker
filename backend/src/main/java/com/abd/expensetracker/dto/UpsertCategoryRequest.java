package com.abd.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertCategoryRequest {
    @NotBlank
    private String name;
    private String description;
}
