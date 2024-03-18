package com.trevortran.expensetracker.repository;

import com.trevortran.expensetracker.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindExistingUserByEmail() {
        User user = new User("test@gmail.com", "foo", "foo", "123", null);
        userRepository.save(user);
        Optional<User> actual = userRepository.findByEmail("test@gmail.com");
        assertTrue(actual.isPresent());
        assertEquals(user, actual.get());
    }

    @Test
    void testFindNonExistingUserByEmail() {
        User user = new User("test@gmail.com", "foo", "foo", "123", null);
        userRepository.save(user);
        Optional<User> actual = userRepository.findByEmail("foo@gmail.com");
        assertFalse(actual.isPresent());
    }
}
