DELIMITER //
CREATE PROCEDURE startTime(IN user_ID_in INT)
  BEGIN
    DECLARE FK_user INT DEFAULT 0;
    DECLARE currentTime datetime DEFAULT current_timestamp();
    SET FK_user = user_ID_in;

    INSERT INTO timespent (FK_user_id, start_time) values (user_ID_in, currentTime);
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE calculateTotalTime()
  BEGIN
    UPDATE timespent
    SET time_spent = timediff(timespent.last_activity, timespent.start_time)
    WHERE time_spent is NULL;
  END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE endTime(IN user_ID INT)
  BEGIN
    DECLARE FK_user int default 0;
    DECLARE currentTime datetime DEFAULT current_timestamp();
    SET FK_user = user_ID;

    UPDATE timespent
    set last_activity = currentTime
    where FK_user_id = FK_user
          and start_time between (currentTime - interval 1 hour) and currentTime and last_activity is null;
    call calculatetotaltime();

  END//
DELIMITER ;

ALTER TABLE votes
  ADD COLUMN time_placed DATETIME NULL
  AFTER FK_user_id;

DELIMITER //
CREATE TRIGGER includeTimeInVotes
  BEFORE INSERT
  ON votes
  FOR EACH ROW
  BEGIN
    SET NEW.time_placed = current_timestamp();
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE checkUserActivity()

  BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE voteplaced datetime default null;
    DECLARE lastseen DATETIME default null;
    DECLARE userid int default 0;
    DECLARE find CURSOR FOR select
                              votes.FK_user_id,
                              votes.time_placed,
                              timespent.last_activity
                            from votes
                              inner join timespent on votes.FK_user_id = timespent.FK_user_id
                            where time_placed in (select max(time_placed)
                                                  from votes
                                                  group by FK_user_id) and timespent.last_activity is null
                            order by time_placed desc;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    open find;

    lastActive : LOOP
      FETCH find
      into userid, voteplaced, lastseen;
      IF done
      THEN
        LEAVE lastActive;
      END IF;
      IF voteplaced NOT between (current_timestamp() - interval 1 hour) and current_timestamp()
      THEN
        UPDATE timespent
        SET last_activity = voteplaced
        where last_activity is null and FK_user_id = userid;
      elseif voteplaced between (current_timestamp() - interval 1 hour) and current_timestamp()
        THEN
          SELECT 'user still active';
      END IF;
    end loop;
    call calculatetotalTime();
  END //
DELIMITER ;
