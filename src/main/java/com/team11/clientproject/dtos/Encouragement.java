package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stores a single encouragement.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Encouragement {
    private int id;
    private String path;
    private String text;
    private int numberOfImages;
    private boolean logoutButton;
}
