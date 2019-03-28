package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.User;
import com.team11.clientproject.dtos.UserTrustworthiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Stores/retrieves users
 */
@Repository
@Transactional
public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Maps rows of type user
     */
    private RowMapper<User> userMapper() {
        return (rs, i) -> {
            var user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setRole(rs.getString("role_name"));
            user.setActivationUUID(rs.getString("activation_uuid"));
            user.setEnabled(rs.getBoolean("enabled"));
            return user;
        };
    }

    /**
     * Finds a user by username
     *
     * @param username the username
     * @return the user
     */
    public Optional<User> findUserByUsername(String username) {
        return this.jdbcTemplate.query("SELECT * FROM users JOIN roles ON users.FK_role_id=roles.role_id WHERE username = ?",
                new Object[]{username}, userMapper()).stream().findFirst();
    }


    /**
     * Creates a user with a USER role
     */
    @Override
    public void registerUser(User user) {
        jdbcTemplate.update("INSERT INTO users (username,password,first_name,last_name,email,FK_role_id, enabled, activation_uuid)" +
                "VALUES(?,?,?,?,?,2,?,?)", new Object[]{user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isEnabled(), user.getActivationUUID()});
    }

    /**
     * Updates the user's trustworthiness.
     */
    public void updateTrustworthiness() {
        jdbcTemplate.update("CALL update_trustworthiness_mv();");
    }

    /**
     * Maps UserTrustworthiness objects
     *
     */
    public RowMapper<UserTrustworthiness> userTrustworthinessRowMapper() {
        return (rs, i) -> {
            var instance = new UserTrustworthiness();
            instance.setId(rs.getInt("user_id"));
            instance.setUsername(rs.getString("username"));
            instance.setTrustworthiness(rs.getDouble("user_trustworthiness"));
            return instance;
        };
    }

    /**
     * Gets trustworthiness for all of the users
     *
     * @return trustworthiness for all users
     */
    @Override
    public List<UserTrustworthiness> getUserTrustworthinessForAllUsers() {
        return jdbcTemplate.query("SELECT user_id,username,user_trustworthiness FROM user_trustworthiness_mv" +
                " JOIN users ON user_trustworthiness_mv.FK_user_id=users.user_id", userTrustworthinessRowMapper());
    }

    @Override
    public Optional<User> findUserByActivationUUID(String activationUUID) {
        return this.jdbcTemplate.query("SELECT * FROM users JOIN roles ON users.FK_role_id=roles.role_id WHERE activation_uuid = ?",
                new Object[]{activationUUID}, userMapper()).stream().findFirst();
    }

    @Override
    public void activateUser(User user) {
        this.jdbcTemplate.update("UPDATE users SET enabled=TRUE WHERE user_id=?", user.getId());
    }
}
