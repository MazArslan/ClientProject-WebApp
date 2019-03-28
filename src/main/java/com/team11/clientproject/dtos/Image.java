package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Stores an image to be voted on
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private int id;
    private String path;
    private List<String> additionalImages;
}
