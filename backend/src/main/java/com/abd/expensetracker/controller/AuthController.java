package com.abd.expensetracker.controller;

import com.abd.expensetracker.dto.AuthResponse;
import com.abd.expensetracker.dto.LoginRequest;
import com.abd.expensetracker.dto.RegisterRequest;
import com.abd.expensetracker.model.User;
import com.abd.expensetracker.repository.UserRepository;
import com.abd.expensetracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        String token = authService.register(req);
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        String token = authService.login(req);
        return new AuthResponse(token);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // return safty copy of the user's info without pass
        return ResponseEntity.ok(new ProfileDto(user.getId(), user.getFullName(), user.getEmail(), user.getRole()));
    }

    // DTO 
    private record ProfileDto(Long id, String fullName, String email, com.abd.expensetracker.model.Role role) {}
}