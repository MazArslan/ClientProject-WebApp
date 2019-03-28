DELIMITER //
CREATE FUNCTION get_image_descriptor(image_ranking DOUBLE)
  RETURNS TEXT
DETERMINISTIC
  BEGIN
    DECLARE result TEXT;
    IF image_ranking > 0.7
    THEN
      SET result = "GOOD";
    ELSEIF image_ranking > 0.3
      THEN
        SET result = "CONTROVERSIAL";
    ELSE
      SET result = "BAD";
    END IF;
    RETURN result;
  END //
DELIMITER ;
CREATE VIEW image_descriptions AS
  SELECT
    image_id,
    path,
    get_image_descriptor(get_image_ranking(image_id)) as description
  FROM images;

