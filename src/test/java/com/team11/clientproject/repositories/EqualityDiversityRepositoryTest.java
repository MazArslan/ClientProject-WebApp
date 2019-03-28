package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.EqualityDiversity;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class EqualityDiversityRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EqualityDiversityRepository equalityDiversityRepository;

    @Test
    @Transactional
    public void testAddEntry() {
        var equalityDiversity = new EqualityDiversity();
        var user = new User();
        user.setId(2);
        equalityDiversity.setCountry("uk");
        equalityDiversity.setEthnicity("white");
        equalityDiversity.setGender("male");
        equalityDiversity.setPostcodeStart("cf10");
        equalityDiversity.setUser(user);
        equalityDiversity.setSexuality("straight");
        this.equalityDiversityRepository.insertEqualityDiversity(equalityDiversity);
        jdbcTemplate.queryForObject("SELECT * FROM equality_diversity ORDER BY equality_diversity_id DESC LIMIT 1", (rs, i) -> {
            assertEquals(rs.getString("country"), "uk");
            assertEquals(rs.getString("ethnicity"), "white");
            assertEquals(rs.getString("gender"), "male");
            assertEquals(rs.getString("postcode_start"), "cf10");
            assertEquals(rs.getString("sexuality"), "straight");
            return true;
        });

    }

    @Test
    @Transactional
    public void testAddEntrySomeNull() {
        var equalityDiversity = new EqualityDiversity();
        var user = new User();
        user.setId(2);

        equalityDiversity.setCountry("uk");
        equalityDiversity.setEthnicity(null);
        equalityDiversity.setGender(null);
        equalityDiversity.setPostcodeStart(null);
        equalityDiversity.setUser(user);
        equalityDiversity.setSexuality("straight");
        this.equalityDiversityRepository.insertEqualityDiversity(equalityDiversity);
        jdbcTemplate.queryForObject("SELECT * FROM equality_diversity ORDER BY equality_diversity_id DESC LIMIT 1", (rs, i) -> {
            assertEquals(rs.getString("country"), "uk");
            assertEquals(rs.getString("ethnicity"), null);
            assertEquals(rs.getString("gender"), null);
            assertEquals(rs.getString("postcode_start"), null);
            assertEquals(rs.getString("sexuality"), "straight");
            return true;
        });

    }

    @Test
    @Transactional
    public void testAddEntryAllNull() {
        var equalityDiversity = new EqualityDiversity();
        var user = new User();
        user.setId(2);

        equalityDiversity.setCountry(null);
        equalityDiversity.setUser(user);
        equalityDiversity.setEthnicity(null);
        equalityDiversity.setGender(null);
        equalityDiversity.setPostcodeStart(null);
        equalityDiversity.setSexuality(null);
        this.equalityDiversityRepository.insertEqualityDiversity(equalityDiversity);
        jdbcTemplate.queryForObject("SELECT * FROM equality_diversity ORDER BY equality_diversity_id DESC LIMIT 1", (rs, i) -> {
            assertEquals(rs.getString("country"), null);
            assertEquals(rs.getString("ethnicity"), null);
            assertEquals(rs.getString("gender"), null);
            assertEquals(rs.getString("postcode_start"), null);
            assertEquals(rs.getString("sexuality"), null);
            return true;
        });

    }

    @Test
    @Transactional
    public void testCheckHasUserFilledIn() {
        var user = new User();
        user.setId(2);
        assertEquals((boolean) equalityDiversityRepository.findUserCompletion(user), true);

    }

}
