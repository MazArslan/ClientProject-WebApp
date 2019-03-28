CREATE TABLE sample_images (
  sample_image_id int AUTO_INCREMENT PRIMARY KEY,
  path            VARCHAR(255) NOT NULL UNIQUE,
  is_correct      TINYINT(1)
)
  ENGINE = INNODB;