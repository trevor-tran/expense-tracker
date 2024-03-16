package com.trevortran.expensetracker.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category implements Comparable<Category>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Category o) {
        return name.compareTo(o.name);
    }
}
