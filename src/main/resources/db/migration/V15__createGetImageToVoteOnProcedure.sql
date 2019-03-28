DELIMITER //
CREATE PROCEDURE get_recommended_image(user_id_input int)
  BEGIN
    DECLARE num_rows INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
      ROLLBACK;
    END;
    START TRANSACTION;
    -- calculates if there are any images the user hasn't voted on
    SELECT COUNT(*)
    FROM images
      LEFT JOIN votes ON images.image_id = FK_image_id
    WHERE image_id NOT IN (SELECT FK_image_id
                           FROM votes
                           WHERE FK_user_id = user_id_input)
    INTO num_rows;
    IF num_rows > 0
    THEN
      -- if there are, then select one of them
      SELECT
        COUNT(*) * IF(SUM(is_vote_up) / COUNT(*) < 1 - SUM(is_vote_up) / COUNT(*), SUM(is_vote_up) / COUNT(*),
                      1 - SUM(is_vote_up) / COUNT(*)) as controversyRating,
        image_id,
        path
      FROM images
        LEFT JOIN votes ON images.image_id = FK_image_id
      GROUP BY (image_id)
      HAVING image_id NOT IN (SELECT FK_image_id
                              FROM votes
                              WHERE FK_user_id = user_id_input)
      ORDER BY controversyRating ASC
      LIMIT 1;
    ELSE
      -- if there aren't select one that the user has already voted on
      -- this is a full query for future expandability.
      SELECT
        COUNT(*) * IF(SUM(is_vote_up) / COUNT(*) < 1 - SUM(is_vote_up) / COUNT(*), SUM(is_vote_up) / COUNT(*),
                      1 - SUM(is_vote_up) / COUNT(*)) as controversyRating,
        image_id,
        path
      FROM images
        LEFT JOIN votes ON images.image_id = FK_image_id
      GROUP BY (image_id)
      ORDER BY RAND()
      LIMIT 1;
    END IF;
    COMMIT;
  END//
DELIMITER ;
