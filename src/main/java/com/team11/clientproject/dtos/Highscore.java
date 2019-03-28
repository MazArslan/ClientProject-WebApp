package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * Stores a single entry in a highscore board
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Highscore {
    private int score;
    private String username;
}
