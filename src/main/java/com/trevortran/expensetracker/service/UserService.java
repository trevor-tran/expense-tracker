package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
}
