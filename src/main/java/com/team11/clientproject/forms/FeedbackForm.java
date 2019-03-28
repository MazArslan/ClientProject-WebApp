package com.team11.clientproject.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Stores a feedback form.
 */
@Data
@AllArgsConstructor
public class FeedbackForm {

    @NotNull
    private int question1;
}
