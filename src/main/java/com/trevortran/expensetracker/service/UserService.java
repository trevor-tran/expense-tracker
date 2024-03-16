package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.User;
import com.trevortran.expensetracker.model.UserCreationDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    void create(UserCreationDTO userCreationDTO);
    UserDetails loadUserByUsername(String email);
}
