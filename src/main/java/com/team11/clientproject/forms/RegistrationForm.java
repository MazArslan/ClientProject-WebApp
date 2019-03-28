package com.team11.clientproject.forms;

import com.team11.clientproject.validations.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Contains the reigistration page.
 */
@Data
@AllArgsConstructor
public class RegistrationForm {
    @NotEmpty
    @UniqueConstraint(table = "users", field = "username")
    private String username;
    @Size(min = 5, max = 20)
    private String password;
    @NotEmpty(message = "Please enter first name")
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @UniqueConstraint(field = "email", table = "users")
    private String emailAddress;
}

