CREATE TABLE encouragement (
  encouragement_id       INT PRIMARY KEY AUTO_INCREMENT,
  number_of_images       INT          NOT NULL,
  encouraging_text       VARCHAR(255) NOT NULL,
  encouraging_image_path VARCHAR(255) NOT NULL
);

INSERT INTO encouragement (number_of_images, encouraging_text, encouraging_image_path)
VALUES (3, 'Brilliant Start!', '/images/encouragement/1.png'),
  (6, 'Good going', '/images/encouragement/2.png'),
  (10, 'Almost there', '/images/encouragement/3.png');