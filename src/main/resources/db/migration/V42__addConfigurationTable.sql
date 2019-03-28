CREATE TABLE configuration (
  name  VARCHAR(20) PRIMARY KEY,
  value INT
);
INSERT INTO configuration (name, value)
VALUES ("email_authentication", 1);