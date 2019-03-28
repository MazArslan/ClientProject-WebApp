ALTER TABLE images
  DROP COLUMN known_status;
DROP VIEW image_descriptions;
CREATE VIEW image_descriptions AS
  SELECT
    image_id,
    path,
    get_image_descriptor(get_image_ranking(image_id)) as description
  FROM images;
