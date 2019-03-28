DELIMITER //
CREATE TRIGGER votes_ban_updates
  BEFORE UPDATE
  ON votes
  FOR EACH ROW
  BEGIN
    SIGNAL SQLSTATE VALUE '45000'
    SET MESSAGE_TEXT = 'Votes are not updatabable.';
  END//
DELIMITER ;