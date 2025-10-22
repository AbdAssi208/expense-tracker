package com.abd.expensetracker.service;

import com.abd.expensetracker.dto.LoginRequest;
import com.abd.expensetracker.dto.RegisterRequest;
import com.abd.expensetracker.model.Role;
import com.abd.expensetracker.model.User;
import com.abd.expensetracker.repository.UserRepository;
import com.abd.expensetracker.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public String register(RegisterRequest req) {
        String fullName = req.getFullName() == null ? null : req.getFullName().trim();
        String email    = req.getEmail() == null ? null : req.getEmail().trim().toLowerCase();
        String password = req.getPassword();

        if (fullName == null || email == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing fields");
        }
        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already used");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);

        
        return jwtService.generateToken(user.getUsername());
    }

   
    public String login(LoginRequest req) {
        String email = req.getEmail() == null ? null : req.getEmail().trim().toLowerCase();
        String password = req.getPassword();
        if (email == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing credentials");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return jwtService.generateToken(email);
    }
}
