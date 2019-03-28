package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class UsersRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Transactional
    @Rollback
    public void testAddUser() {
        var user = new User();
        user.setUsername("testuser1");
        user.setPassword("invalidPassword");
        user.setEmail("test123@test.com");
        user.setLastName("123456");
        user.setFirstName("12345");
        user.setActivationUUID("thisisauuidright");

//        jdbcTemplate.update("LOCK TABLES users WRITE, roles WRITE");
        usersRepository.registerUser(user);
        jdbcTemplate.queryForObject("SELECT * FROM users  JOIN roles on users.FK_role_id=roles.role_id ORDER BY user_id DESC LIMIT 1", (rs, i) -> {
            assertEquals(rs.getString("username"), user.getUsername());
            assertEquals(rs.getString("password"), user.getPassword());
            assertEquals(rs.getString("first_name"), user.getFirstName());
            assertEquals(rs.getString("last_name"), user.getLastName());
            assertEquals(rs.getString("email"), user.getEmail());
            assertEquals(rs.getString("role_name"), "ROLE_USER");
            assertEquals(rs.getBoolean("enabled"), false);
            assertEquals(rs.getString("activation_uuid"), "thisisauuidright");

            return true;
        });
//        jdbcTemplate.update("UNLOCK TABLES");

    }

    @Test
    @Transactional()
    @Rollback(true)
    public void testFindUserByUsername() {
//        jdbcTemplate.update("LOCK TABLES users WRITE, roles WRITE");
        var user = new User();
        user.setUsername("testuser12");
        user.setPassword("invalidPassword");
        user.setEmail("test1234@test.com");
        user.setLastName("123456");
        user.setFirstName("12345");

        usersRepository.registerUser(user);
        var rs = usersRepository.findUserByUsername("testuser12").get();
        assertEquals(user.getEmail(), rs.getEmail());
        assertEquals(user.getPassword(), rs.getPassword());
        assertEquals(user.getFirstName(), rs.getFirstName());
        assertEquals(user.getLastName(), rs.getLastName());
        assertEquals(user.getFirstName(), rs.getFirstName());
        assertEquals(user.getUsername(), rs.getUsername());
//        jdbcTemplate.update("UNLOCK TABLES");
    }

    @Test
    @Transactional
    public void testMaterialisedViewUpdates() {
        usersRepository.updateTrustworthiness();
        var admin1 = usersRepository.getUserTrustworthinessForAllUsers().stream().filter((entry) -> {
            return entry.getId() == 1;
        }).findFirst();
        jdbcTemplate.update("INSERT INTO votes(FK_user_id, FK_image_id, is_vote_up)" +
                " VALUES(1,11,1)");
        usersRepository.updateTrustworthiness();

        var admin2 = usersRepository.getUserTrustworthinessForAllUsers().stream().filter((entry) -> {
            return entry.getId() == 1;
        }).findFirst();
        assertTrue(Double.compare(admin1.get().getTrustworthiness(), admin2.get().getTrustworthiness()) != 0);
    }

    @Test
    @Transactional
    public void testActivation() {
        var user = new User();
        user.setUsername("testuser12");
        user.setPassword("invalidPassword");
        user.setEmail("test1234@test.com");
        user.setLastName("123456");
        user.setFirstName("12345");
        user.setActivationUUID("thisisauuidright");
        usersRepository.registerUser(user);
        var id = jdbcTemplate.queryForObject("SELECT MAX(user_id) FROM users", Integer.class);
        user.setId(id);
        usersRepository.activateUser(user);
        var success = jdbcTemplate.queryForObject("SELECT enabled FROM users WHERE user_id=?", new Object[]{id}, Boolean.class);
        assertTrue(success);
    }
}
