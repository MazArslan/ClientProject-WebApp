CREATE TABLE roles
(
  role_id   INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(30) NOT NULL UNIQUE
);
CREATE TABLE users
(
  user_id    INT PRIMARY KEY  AUTO_INCREMENT NOT NULL,
  username   VARCHAR(255)                    NOT NULL UNIQUE,
  password   VARCHAR(128)                    NOT NULL,
  first_name VARCHAR(255)                    NOT NULL,
  last_name  VARCHAR(255)                    NOT NULL,
  email      VARCHAR(255)                    NOT NULL UNIQUE,
  FK_role_id INT                             NOT NULL,
  FOREIGN KEY (FK_role_id) REFERENCES roles (role_id)
);

INSERT INTO roles (role_id, role_name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');
