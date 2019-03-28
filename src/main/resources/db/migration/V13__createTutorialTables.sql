CREATE TABLE tutorials (
  tutorial_id     INT AUTO_INCREMENT PRIMARY KEY,
  tutorial_name   VARCHAR(25),
  last_updated_on DATETIME
);
-- this shouldn't be updatable
CREATE TABLE tutorial_completion (
  FK_tutorial_id INT,
  FK_user_id     INT,
  completed_on   DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO tutorials (tutorial_name, last_updated_on)
VALUES ('INTRODUCTION', '2018-11-20 00:00:00'),
  ('SAMPLE_IMAGES', '2018-11-20 00:00:00');