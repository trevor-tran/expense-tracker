package com.trevortran.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    List<Transaction> transactions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Collection <Role> roles;

    public User(String email, List<Transaction> transactions) {
        this.email = email;
        this.transactions = transactions;
    }

    public User(String email) {
        this(email, new ArrayList<>());
    }
}
