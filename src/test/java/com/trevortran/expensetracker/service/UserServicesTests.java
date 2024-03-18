package com.trevortran.expensetracker.service;

import com.trevortran.expensetracker.model.UserCreationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class UserServicesTests {

    @Autowired
    private UserService userService;

    @Test
    void testCreateUserSuccessfully() {
        UserCreationDTO userCreationDTO = new UserCreationDTO("foo", "foo", "foo@foo.com", "123", "123");
        userService.create(userCreationDTO);
        UserDetails userDetails = userService.loadUserByUsername("foo@foo.com");
        assertEquals(userCreationDTO.getEmail(), userDetails.getUsername());
    }
}
