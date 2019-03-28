package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stores the user trustworthiness mapping
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTrustworthiness {
    private int id;
    private String username;
    // needs to be a double for nullability;
    private Double trustworthiness;
}
