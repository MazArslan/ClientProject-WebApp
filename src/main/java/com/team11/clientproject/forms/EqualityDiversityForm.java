package com.team11.clientproject.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Stores the equality/diversity form
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EqualityDiversityForm {
    private String gender;
    private String ethnicity;
    private String sexuality;
    private String country;
    private String postcodeStart;
}
