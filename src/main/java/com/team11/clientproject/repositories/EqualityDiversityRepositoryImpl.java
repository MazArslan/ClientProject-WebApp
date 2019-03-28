package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.EqualityDiversity;
import com.team11.clientproject.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Stores and retrieves equality/diversity information
 */
@Repository
public class EqualityDiversityRepositoryImpl implements EqualityDiversityRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EqualityDiversityRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves an equality/diversity entry
     *
     * @param equalityDiversity the EqualityDiversity form
     */
    @Override
    public void insertEqualityDiversity(EqualityDiversity equalityDiversity) {
        jdbcTemplate.update("INSERT INTO equality_diversity(gender,sexuality,ethnicity,country,postcode_start,FK_user_id)" +
                        "VALUES(?,?,?,?,?,?)",
                new Object[]{equalityDiversity.getGender(), equalityDiversity.getSexuality(), equalityDiversity.getEthnicity(), equalityDiversity.getCountry(), equalityDiversity.getPostcodeStart(), equalityDiversity.getUser().getId()});
    }

    /**
     * Checks if the user has completed a tutorial
     */
    @Override
    public Boolean findUserCompletion(User user) {

        return jdbcTemplate.queryForObject("SELECT COUNT(FK_user_id) FROM equality_diversity WHERE FK_user_id = " + user.getId(), Boolean.class);

    }
}
