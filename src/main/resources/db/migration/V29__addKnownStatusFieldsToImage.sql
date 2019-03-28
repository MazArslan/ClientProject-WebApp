ALTER TABLE images
  ADD COLUMN known_status BOOLEAN NULL;
DROP VIEW image_descriptions;
CREATE VIEW image_descriptions AS
  SELECT
    image_id,
    path,
    images.known_status,
    get_image_descriptor(get_image_ranking(image_id)) as description
  FROM images;
DROP FUNCTION select_user_trustworthiness;
-- selects the user trustworthiness ranking
-- on a scale from 0-1
-- based on if their answers match up with the crowd.
-- the more answers that match up with the ranking, the higher the ranking is
DELIMITER //
CREATE FUNCTION select_user_trustworthiness(user_id_given INT)
  RETURNS DOUBLE
READS SQL DATA
  BEGIN
    -- tracks if the cursor is done
    DECLARE done BOOLEAN DEFAULT false;
    -- data from the current iteration of the cursor
    DECLARE cursor_image_id INT;
    DECLARE cursor_is_vote_up BOOLEAN;
    -- trustworthiness - how many images the user has the common opinion at.
    DECLARE trustworthiness DOUBLE DEFAULT 0;
    -- average ranking of other users for the specific image
    DECLARE average_ranking DOUBLE;
    -- number of votes the user has made
    DECLARE votes_count INT DEFAULT 0;
    -- cursor for the users votes
    DECLARE current_known_status BOOLEAN;
    DECLARE votes_cursor CURSOR FOR SELECT
                                      FK_image_id,
                                      is_vote_up
                                    FROM votes
                                    WHERE FK_user_id = user_id_given;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN votes_cursor;
    votes_loop: LOOP
      FETCH votes_cursor
      INTO cursor_image_id, cursor_is_vote_up;
      -- loop terminator
      IF done
      THEN
        LEAVE votes_loop;
      END IF;
      -- save the average into average_ranking
      SELECT avg(is_vote_up)
      FROM votes
      WHERE FK_image_id = cursor_image_id
      INTO average_ranking;
      -- get known status
      SELECT known_status
      FROM images
      WHERE image_id = cursor_image_id
      INTO current_known_status;
      -- if the user's vote matches the crowd's, increment it.
      IF current_known_status IS NOT NULL
      THEN
        IF (current_known_status = cursor_is_vote_up)
        THEN
          SET trustworthiness = trustworthiness + 1;
        ELSE
          -- count as 3 wrong votes if they got the image wrong
          SET votes_count = votes_count + 3;
        END IF;
      ELSE
        IF (average_ranking > 0.5 AND cursor_is_vote_up = TRUE) OR
           (average_ranking <= 0.5 AND cursor_is_vote_up = FALSE)
        THEN
          SET trustworthiness = trustworthiness + 1;
        END IF;
      END IF;
      -- increase this counter either way
      SET votes_count = votes_count + 1;
    END LOOP;
    -- return the trustiness divided by the vote count
    IF votes_count = 0 OR trustworthiness = 0
    THEN
      RETURN 0;
    END IF;
    RETURN (trustworthiness / votes_count);
  END //
DELIMITER ;