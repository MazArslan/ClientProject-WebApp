DELIMITER //
CREATE TRIGGER validate_ethnicity
  AFTER INSERT
  ON equality_diversity
  FOR EACH ROW
  BEGIN
    IF new.ethnicity NOT IN ('White British', 'White', 'Black', 'Other')
    THEN
      SIGNAL SQLSTATE VALUE '45000'
      SET MESSAGE_TEXT = 'Invalid ethnicity ';
    END IF;
  END//
DELIMITER ;