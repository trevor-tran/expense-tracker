package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    void saveUser(User user);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(UUID id);
}
