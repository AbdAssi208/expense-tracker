package com.abd.expensetracker.service.mapper;

import com.abd.expensetracker.dto.TransactionDto;
import com.abd.expensetracker.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionDto toDto(Transaction t){
        if (t == null) return null;
        TransactionDto dto = new TransactionDto();
        dto.setId(t.getId());
        dto.setType(t.getType());
        dto.setAmount(t.getAmount());
        dto.setDate(t.getDate());
        dto.setNote(t.getNote());
        dto.setDescription(t.getDescription()); // <-- نضيف الوصف
        if (t.getCategory() != null){
            dto.setCategoryId(t.getCategory().getId());
            dto.setCategoryName(t.getCategory().getName());
        }
        return dto;
    }
}
