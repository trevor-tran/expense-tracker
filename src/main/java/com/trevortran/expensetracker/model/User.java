package com.trevortran.expensetracker.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.*;

/**
 * User Entity
 */
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
    @JoinColumn(name = "userId")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, password);
    }
}
