package com.team11.clientproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Stores an equality/diversity entry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EqualityDiversity {
    private String gender;
    private String sexuality;
    private String ethnicity;
    private String country;
    private String postcodeStart;
    private User user;
}
