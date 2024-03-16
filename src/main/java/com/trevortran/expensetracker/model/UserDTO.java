package com.trevortran.expensetracker.model;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.util.logging.Level;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    String firstName;
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    String lastName;

    public UserDTO(@Pattern(regexp = "[A-Za-z]+$",
            message = "Only alphabetic allowed") String firstName, @Pattern(regexp =
            "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
