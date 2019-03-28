package com.team11.clientproject.validations;

import com.team11.clientproject.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates if a unique field exists in the database already
 */
public class UniqueConstraintValidator implements ConstraintValidator<UniqueConstraint, String> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String field;
    private String table;

    @Override
    public void initialize(UniqueConstraint constraintAnnotation) {
        this.table = constraintAnnotation.table();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + table + " WHERE " + field + "=?", new Object[]{value}, Integer.class) == 0;
    }
}
