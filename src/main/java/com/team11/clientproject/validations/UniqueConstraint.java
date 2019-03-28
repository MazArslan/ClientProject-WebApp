package com.team11.clientproject.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation for existing field
 */
@Documented
@Constraint(validatedBy = UniqueConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueConstraint {
    String message() default "Username already used";

    String field() default "";

    String table() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}