package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Encouragement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EncouragementRepositoryImpl implements EncouragementRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EncouragementRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Row mapper for encouragement
     */
    private RowMapper<Encouragement> encouragementRowMapper() {
        return (rs, i) -> {
            var encouragement = new Encouragement();
            encouragement.setId(rs.getInt("encouragement_id"));
            encouragement.setNumberOfImages(rs.getInt("number_of_images"));
            encouragement.setText(rs.getString("encouraging_text"));
            encouragement.setPath(rs.getString("encouraging_image_path"));
            encouragement.setLogoutButton(rs.getBoolean("logout_button"));
            return encouragement;
        };
    }

    /**
     * gets the necessary encouragement for the number of images  the user has given.
     *
     * @param numberOfImages the number of images to select
     * @return an empty optional if nothing is required, the optional of encouragement otherwise
     */
    @Override
    public Optional<Encouragement> getEncouragementForNumberOfImages(int numberOfImages) {
        return jdbcTemplate.query("SELECT * FROM encouragement WHERE number_of_images=?", new Object[]{numberOfImages}, encouragementRowMapper())
                .stream().findFirst();
    }
}
