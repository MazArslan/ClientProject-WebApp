UPDATE images
SET known_status = FALSE
WHERE image_id = 2;
UPDATE images
SET known_status = TRUE
WHERE image_id = 3;