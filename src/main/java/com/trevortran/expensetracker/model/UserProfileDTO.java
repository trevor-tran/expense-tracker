package com.trevortran.expensetracker.model;

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

    public UserProfileDTO(@Pattern(regexp = "[A-Za-z]+$",
            message = "Only alphabetic allowed") String firstName, @Pattern(regexp =
            "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
