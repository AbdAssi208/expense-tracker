package com.abd.expensetracker.service.impl;

import com.abd.expensetracker.dto.CategoryDto;
import com.abd.expensetracker.dto.UpsertCategoryRequest;
import com.abd.expensetracker.model.Category;
import com.abd.expensetracker.model.User;
import com.abd.expensetracker.repository.CategoryRepository;
import com.abd.expensetracker.repository.UserRepository;
import com.abd.expensetracker.service.CategoryService;
import com.abd.expensetracker.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    private User userByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    @Override
    public List<CategoryDto> listForUser(String email) {
        User u = userByEmail(email);
        return categoryRepository.findByOwner(u).stream().map(mapper::toDto).toList();
    }

    @Override
    public CategoryDto createForUser(String email, UpsertCategoryRequest req) {
        User u = userByEmail(email);
        if (categoryRepository.existsByNameAndOwner(req.getName(), u)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category already exists");
        }
        Category c = new Category();
        c.setName(req.getName());
        c.setOwner(u);
        return mapper.toDto(categoryRepository.save(c));
    }

    @Override
    public CategoryDto updateForUser(String email, Long id, UpsertCategoryRequest req) {
        User u = userByEmail(email);
        Category c = categoryRepository.findByIdAndOwner(id, u)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        c.setName(req.getName());
        return mapper.toDto(categoryRepository.save(c));
    }

    @Override
    public void deleteForUser(String email, Long id) {
        User u = userByEmail(email);
        Category c = categoryRepository.findByIdAndOwner(id, u)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(c);
    }

    @Override
    public CategoryDto getForUser(String email, Long id) {
        User u = userByEmail(email);
        Category c = categoryRepository.findByIdAndOwner(id, u)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return mapper.toDto(c);
    }
}
