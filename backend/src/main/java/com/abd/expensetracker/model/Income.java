package com.abd.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "incomes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Income {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, scale = 2, precision = 12)
    private BigDecimal amount;

    private String source;       
    @Column(nullable = false)
    private LocalDate date;
}

