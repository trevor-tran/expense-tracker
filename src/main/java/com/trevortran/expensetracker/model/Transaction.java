package com.trevortran.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    LocalDate date;
    double amount;
    UUID userId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    public Transaction(UUID userId, LocalDate date, double amount, Category category) {
        this.userId = userId;
        this.date = date;
        this.amount = amount;
        this.category = category;
    }
}
