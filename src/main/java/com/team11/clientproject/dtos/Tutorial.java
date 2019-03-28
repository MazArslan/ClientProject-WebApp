package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a Tutorial instance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tutorial {
    private int id;
    private String name;
    private LocalDateTime lastUpdatedOn;
}
