package com.trevortran.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Indexed;

import java.util.*;

@Entity
@Table(indexes = @Index(columnList = "email", unique = true))
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String email;
    String firstName;
    String lastName;
    String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    List<Transaction> transactions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Collection <Role> roles;

   public User(String email, String firstName, String lastName, String password, List<Transaction> transactions) {
       this.email = email;
       this.firstName = firstName;
       this.lastName = lastName;
       this.password = password;
       this.transactions = transactions;
   }
}
