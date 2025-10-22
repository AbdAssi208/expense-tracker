package com.abd.expensetracker.service;

import com.abd.expensetracker.dto.CategoryDto;
import com.abd.expensetracker.dto.UpsertCategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listForUser(String email);
    CategoryDto createForUser(String email, UpsertCategoryRequest req);
    CategoryDto updateForUser(String email, Long id, UpsertCategoryRequest req);
    void deleteForUser(String email, Long id);
    CategoryDto getForUser(String email, Long id);
}
