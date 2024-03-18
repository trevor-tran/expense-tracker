package com.trevortran.expensetracker.model;

import com.trevortran.expensetracker.util.FieldMatch;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for User Signup Form
 */
@Getter
@Setter
@NoArgsConstructor
@FieldMatch.List({@FieldMatch(first = "password", second = "matchingPassword",
        message = "The password fields must match")})
public class UserCreationDTO {
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String firstName;
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String lastName;
    @Column(unique = true)
    @Email
    private String email;
    @NotBlank(message = "Required")
    private String password;
    @NotBlank(message = "Required")
    private String matchingPassword;

    public UserCreationDTO(@Pattern(regexp = "[A-Za-z]+$",
            message = "Only alphabetic allowed") String firstName, @Pattern(regexp =
            "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName, @Email String
                           email, @NotEmpty(message = " Required") String password,
                   @NotEmpty(message = "Required")String matchingPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }
}