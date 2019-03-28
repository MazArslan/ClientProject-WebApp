package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Stores a sample  image
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleImage {
    private int id;
    private String path;
    private boolean isCorrect;
    private List<String> additionalImages;
}
