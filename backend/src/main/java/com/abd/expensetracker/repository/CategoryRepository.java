package com.abd.expensetracker.repository;

import com.abd.expensetracker.model.Category;
import com.abd.expensetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByOwner(User owner);
    Optional<Category> findByIdAndOwner(Long id, User owner);
    boolean existsByNameAndOwner(String name, User owner);
}
