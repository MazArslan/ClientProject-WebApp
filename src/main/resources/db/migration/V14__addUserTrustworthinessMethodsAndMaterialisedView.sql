DELIMITER //
-- selects the user trustworthiness ranking
-- on a scale from 0-1
-- based on if their answers match up with the crowd.
-- the more answers that match up with the ranking, the higher the ranking is
CREATE FUNCTION select_user_trustworthiness(user_id_given INT)
  RETURNS DOUBLE
READS SQL DATA
  BEGIN
    -- tracks if the cursor is done
    DECLARE done BOOLEAN DEFAULT false;
    -- data from the current iteration of the cursor
    DECLARE cursor_image_id INT;
    DECLARE cursor_is_vote_up BOOLEAN;
    -- trustworthiness - how many immages the user has the common opinion at.
    DECLARE trustworthiness DOUBLE DEFAULT 0;
    -- average ranking of other users for the specific image
    DECLARE average_ranking DOUBLE;
    -- number of votes the user has made
    DECLARE votes_count INT DEFAULT 0;
    -- cursor for the users votes
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
      -- if the user's vote matches the crowd's, increment it.
      IF (average_ranking > 0.5 AND cursor_is_vote_up = TRUE) OR (average_ranking <= 0.5 AND cursor_is_vote_up = FALSE)
      THEN
        SET trustworthiness = trustworthiness + 1;
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
  END//
DELIMITER ;
CREATE TABLE user_trustworthiness_mv (
  FK_user_id           INT,
  user_trustworthiness DOUBLE NULL,
  FOREIGN KEY (FK_user_id) REFERENCES users (user_id)
);
DELIMITER //
CREATE PROCEDURE update_trustworthiness_mv()
  BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
      ROLLBACK;
    END;
    START TRANSACTION;
    DELETE FROM user_trustworthiness_mv;
    INSERT INTO user_trustworthiness_mv
      SELECT
        user_id                              as FK_user_id,
        select_user_trustworthiness(user_id) AS user_trustworthiness
      FROM users;
    COMMIT;
  END//
DELIMITER ;