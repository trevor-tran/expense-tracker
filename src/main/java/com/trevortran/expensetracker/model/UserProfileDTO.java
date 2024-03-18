package com.trevortran.expensetracker.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Data Transfer Object for User Profile Page
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileDTO {
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    String firstName;
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    String lastName;
    @Email
    String email;

    public UserProfileDTO(@Pattern(regexp = "[A-Za-z]+$",
            message = "Only alphabetic allowed") String firstName, @Pattern(regexp =
            "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName, @Email String
            email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
