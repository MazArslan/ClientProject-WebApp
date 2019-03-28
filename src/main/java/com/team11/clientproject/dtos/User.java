package com.team11.clientproject.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Contains the user class.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String activationUUID;
    private boolean enabled;
}
