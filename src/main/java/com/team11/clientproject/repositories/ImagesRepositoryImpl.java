package com.team11.clientproject.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.dtos.ImageDescription;
import com.team11.clientproject.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Stores/retrieves images
 */
@Service
public class ImagesRepositoryImpl implements ImagesRepository {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public ImagesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Contains the image row mapper
     *
     * @return the image row mapper
     */
    private RowMapper<Image> imageMapper() {
        return (rs, i) -> {
            var image = new Image();
            var objectMapper = new ObjectMapper();
            image.setPath(rs.getString("path"));
            image.setId(rs.getInt("image_id"));
            image.setAdditionalImages(StringUtils.convertJsonToListOfStrings(rs.getString("additional_image_paths")));
            return image;
        };
    }

    /**
     * Mapper for imageDescription instances
     */
    private RowMapper<ImageDescription> imageDescriptionRowMapper() {
        return (rs, i) -> {
            var image = new ImageDescription();
            image.setId(rs.getInt("image_id"));
            image.setDescription(rs.getString("description"));
            image.setPath(rs.getString("path"));
            // deals with nullability, mapping with getBoolean doesnt handle false/null
            // reference: https://stackoverflow.com/questions/33266078/java-spring-rowmapper-resultset-integer-null-values
            // accessed 1/12/2018
            image.setKnownStatus((Boolean) rs.getObject("known_status"));
            return image;
        };
    }

    /**
     * Gets a recommended image from the database for the user
     *
     * @param user the user given.
     * @return images a list of images
     */
    @Override
    public Image getImage(User user) {
        return jdbcTemplate.queryForObject("CALL get_recommended_image(?)", new Object[]{user.getId()}, imageMapper());
    }

    /**
     * Gets an image by its ID
     *
     * @param id the ID of the image
     * @return the image
     */
    @Override
    public Optional<Image> getImageById(int id) {
        return jdbcTemplate.query("SELECT * FROM images WHERE image_id=? LIMIT 1", new Object[]{id}, imageMapper()).stream().findFirst();
    }


    /**
     * Gets the ranking for one image
     *
     * @param image the image
     * @return the ranking
     */
    @Override
    public Double getImageRanking(Image image) {
        var value = jdbcTemplate.queryForObject("SELECT get_image_ranking(?)", new Object[]{image.getId()}, Double.class);
        return value;
    }

    /**
     * Gets all images
     */
    @Override
    public List<Image> getAllImages() {
        return jdbcTemplate.query("SELECT * FROM images", imageMapper());
    }

    /**
     * Gets the description for all images
     * @return the description
     */
    @Override
    public List<ImageDescription> getAllImagesDescriptions() {
        return jdbcTemplate.query("SELECT * FROM image_descriptions", imageDescriptionRowMapper());
    }

    @Override
    public void addImage(String path, Boolean trustworthy, List<String> additionalImagePaths) {
        jdbcTemplate.update("INSERT INTO images(path,known_status, additional_image_paths)" +
                "VALUES(?,?,?)", new Object[]{path, trustworthy, StringUtils.convertListOfTypeToJson(additionalImagePaths)});
    }
}
