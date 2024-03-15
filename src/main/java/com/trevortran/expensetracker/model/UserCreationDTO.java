package com.trevortran.expensetracker.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// todo: password matching and complexity
//public record UserCreationDTO(@NotBlank @Email String email,
//                              @NotBlank @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String firstName,
//                              @NotBlank @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName,
//                              @NotBlank String password,
//                              @NotBlank String matchingPassword) {
//}

@Getter
@Setter
@NoArgsConstructor
public class UserCreationDTO {
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String firstName;
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String lastName;
    @Column(unique = true)
    @Email
    private String email;
    @NotEmpty(message = "Required")
    private String password;
    @NotEmpty(message = "Required")
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