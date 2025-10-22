package com.abd.expensetracker.service;

import com.abd.expensetracker.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmailOrThrow(String email);
    String getCurrentUsername();
}
