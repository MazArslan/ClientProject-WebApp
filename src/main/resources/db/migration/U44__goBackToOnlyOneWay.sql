DROP FUNCTION get_image_ranking;
DELIMITER //
CREATE FUNCTION get_image_ranking(image_id_given INT)
  RETURNS DOUBLE
READS SQL DATA
  BEGIN
    -- tracks if the cursor is done
    DECLARE done BOOLEAN DEFAULT false;
    -- data from the current iteration of the cursor
    DECLARE cursor_user_id INT;
    DECLARE cursor_is_vote_up BOOLEAN;
    -- stores the user trustworthiness of the current user
    DECLARE user_trustworthiness_storage DOUBLE;
    -- the sum of the votes for the image
    DECLARE image_current_sum DOUBLE DEFAULT 0;
    -- the number of votes
    DECLARE image_votes_count INT DEFAULT 0;
    DECLARE votes_cursor CURSOR FOR SELECT
                                      FK_user_id,
                                      is_vote_up
                                    FROM votes
                                    WHERE FK_image_id = image_id_given;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    OPEN votes_cursor;
    votes_loop: LOOP
      FETCH votes_cursor
      INTO cursor_user_id, cursor_is_vote_up;
      -- loop terminator
      IF done
      THEN
        LEAVE votes_loop;
      END IF;
      -- get the user's trustworthiness from the materialised view
      IF cursor_user_id IS NOT NULL
      THEN
        SELECT user_trustworthiness
        FROM user_trustworthiness_mv
        WHERE FK_user_id = cursor_user_id
        INTO user_trustworthiness_storage;
        -- increase the counter in either direction by their trustworthiness
        SELECT
          image_current_sum + IF(cursor_is_vote_up = 1, user_trustworthiness_storage, -user_trustworthiness_storage)
        INTO image_current_sum;
        -- increase image counter
        SET image_votes_count = image_votes_count + 1;
      END IF;
    END LOOP;
    -- fallback for divison by 0
    IF image_current_sum = 0 OR image_votes_count = 0
    THEN
      RETURN 0;
    END IF;
    -- normalise the number so it's a number between 0 and 1
    RETURN ((image_current_sum / image_votes_count) + 1) / 2;
  END //
DELIMITER ;
