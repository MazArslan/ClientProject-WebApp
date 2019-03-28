package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.SampleImage;
import com.team11.clientproject.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Stores/retrieves sample images
 */
@Service
public class SampleImagesRepositoryImpl implements SampleImagesRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SampleImagesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Contains the row mapper for sample images
     *
     * @return the row mapper
     */
    private RowMapper<SampleImage> sampleImageRowMapper() {
        return (rs, i) -> {
            var sampleImage = new SampleImage();
            sampleImage.setPath(rs.getString("path"));
            sampleImage.setId(rs.getInt("sample_image_id"));
            sampleImage.setCorrect(rs.getBoolean("is_correct"));
            sampleImage.setAdditionalImages(StringUtils.convertJsonToListOfStrings(rs.getString("additional_image_paths")));
            return sampleImage;
        };
    }

    /**
     * Gets the sample images to give to the user
     *
     * @return
     */
    @Override
    public List<SampleImage> getSampleImages() {
        var sampleImages = jdbcTemplate.query("SELECT * FROM sample_images ORDER BY RAND() LIMIT 10", sampleImageRowMapper());
        return sampleImages;
    }


}
