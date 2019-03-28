package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an image's description for  admins
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDescription {
    private int id;
    private String path;
    private String description;
    private Boolean knownStatus;
}
