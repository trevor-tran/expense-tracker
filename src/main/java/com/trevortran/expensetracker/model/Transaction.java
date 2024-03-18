package com.trevortran.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    LocalDate date;
    String description;
    double amount;
    UUID userId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    public Transaction(UUID userId, LocalDate date, String description, double amount, Category category) {
        this.userId = userId;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(date, that.date) && Objects.equals(description, that.description) && Objects.equals(userId, that.userId) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, description, amount, userId, category);
    }
}
