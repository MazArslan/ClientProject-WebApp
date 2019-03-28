CREATE TABLE scoreboard_mv
(
  FK_user_id INT,
  highscore  INT,
  username   VARCHAR(255),
  FOREIGN KEY (FK_user_id) REFERENCES users (user_id)
);

DELIMITER //
CREATE PROCEDURE update_scoring_chart()
  BEGIN
    DELETE FROM scoreboard_mv;
    INSERT INTO scoreboard_mv (FK_user_id, highscore, username)
      (SELECT
         FK_user_id,
         count(*) AS highscore,
         username
       FROM votes
         JOIN users ON users.user_id = votes.FK_user_id
       GROUP BY FK_user_id
       HAVING FK_user_id IS NOT NULL
       ORDER BY highscore DESC);
  END//
DELIMITER ;

DELIMITER //
CREATE TRIGGER votes_update_mv
  AFTER INSERT
  ON votes
  FOR EACH ROW
  BEGIN
    UPDATE scoreboard_mv
    SET highscore = highscore + 1
    WHERE FK_user_id = new.FK_user_id;
  END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_scoring_chart(user_id_given INT)
  BEGIN
    (SELECT
       username,
       FK_user_id as user_id,
       highscore
     FROM scoreboard_mv
     WHERE FK_user_id IS NOT NULL
     ORDER BY highscore DESC
     LIMIT 5)
    UNION
    SELECT
      username,
      FK_user_id as user_id,
      highscore
    FROM scoreboard_mv
    WHERE FK_user_id = user_id_given;

  END//
DELIMITER ;