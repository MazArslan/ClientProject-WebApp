CREATE TABLE timespent (
  session_num   INT NOT NULL AUTO_INCREMENT,
  FK_user_id    INT,
  start_time    DATETIME,
  last_activity DATETIME,
  time_spent    TIME,
  PRIMARY KEY (`session_num`),
  FOREIGN KEY (FK_user_id) REFERENCES users (user_id)
);
