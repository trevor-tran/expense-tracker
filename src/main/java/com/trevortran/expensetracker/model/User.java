package com.trevortran.expensetracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String email;
    @OneToMany
    @JoinColumn(name = "transaction_id")
    Set<Transaction> transactions;

    public User(String email, Set<Transaction> transactions) {
        this.email = email;
        this.transactions = transactions;
    }

    public User(String email) {
        this(email, new HashSet<>());
    }
}
