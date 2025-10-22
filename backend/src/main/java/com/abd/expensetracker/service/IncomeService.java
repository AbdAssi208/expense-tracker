package com.abd.expensetracker.service;

import com.abd.expensetracker.dto.CreateIncomeRequest;
import com.abd.expensetracker.dto.IncomeDto;
import com.abd.expensetracker.model.Income;
import com.abd.expensetracker.model.User;
import com.abd.expensetracker.repository.IncomeRepository;
import com.abd.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepo;
    private final UserRepository userRepo;

    private User userByEmail(String email){ return userRepo.findByEmail(email).orElseThrow(); }

    public IncomeDto create(String email, CreateIncomeRequest req){
        User u = userByEmail(email);
        Income i = Income.builder()
                .user(u)
                .amount(req.amount())
                .source(req.source())
                .date(req.date()==null? LocalDate.now(): req.date())
                .build();
        i = incomeRepo.save(i);
        return toDto(i);
    }

    public List<IncomeDto> list(String email){
        User u = userByEmail(email);
        return incomeRepo.findByUserIdOrderByDateDesc(u.getId()).stream().map(this::toDto).toList();
    }

    public void delete(String email, Long id){
        User u = userByEmail(email);
        Income i = incomeRepo.findById(id).orElseThrow();
        if(!i.getUser().getId().equals(u.getId())) throw new RuntimeException("Forbidden");
        incomeRepo.delete(i);
    }

    private IncomeDto toDto(Income i){ return new IncomeDto(i.getId(), i.getAmount(), i.getSource(), i.getDate()); }
}


