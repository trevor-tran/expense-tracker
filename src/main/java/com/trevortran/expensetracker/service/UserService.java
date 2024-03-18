package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.User;
import com.trevortran.expensetracker.model.UserCreationDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

/**
 * Define available abstract methods for User
 */
public interface UserService extends UserDetailsService {
    /**
     * create a user account
     * @param userCreationDTO user creation info
     */
    void create(UserCreationDTO userCreationDTO);

    /**
     * retrieve user account by email
     * @param email lookup user by email
     * @return user account info
     */
    UserDetails loadUserByUsername(String email);
}
