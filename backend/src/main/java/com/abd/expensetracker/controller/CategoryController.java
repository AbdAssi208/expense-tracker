package com.abd.expensetracker.controller;

import com.abd.expensetracker.dto.CategoryDto;
import com.abd.expensetracker.dto.UpsertCategoryRequest;
import com.abd.expensetracker.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private String requireEmail(Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return auth.getName();
    }

    @Operation(summary = "List categories for current user")
    @GetMapping
    public List<CategoryDto> list(Authentication auth) {
        return categoryService.listForUser(requireEmail(auth));
    }

    @Operation(summary = "Create category")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto create(@RequestBody UpsertCategoryRequest req, Authentication auth) {
        return categoryService.createForUser(requireEmail(auth), req);
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable("id") Long id, Authentication auth) {
        return categoryService.getForUser(requireEmail(auth), id);
    }

    @Operation(summary = "Update category")
    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable("id") Long id,
                              @RequestBody UpsertCategoryRequest req,
                              Authentication auth) {
        return categoryService.updateForUser(requireEmail(auth), id, req);
    }

    @Operation(summary = "Delete category")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, Authentication auth) {
        categoryService.deleteForUser(requireEmail(auth), id);
    }
}
