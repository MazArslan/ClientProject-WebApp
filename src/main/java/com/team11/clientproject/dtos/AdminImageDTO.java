package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an image and its ranking for admins
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminImageDTO {
    private Double ranking;
    private String path;
    private int id;
}
