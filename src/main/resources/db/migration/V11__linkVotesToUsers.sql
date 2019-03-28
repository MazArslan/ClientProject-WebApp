-- migrates by putting in an anonymous user, that we assign all existing votes to.
LOCK TABLES votes WRITE, flyway_schema_history WRITE, users WRITE;
ALTER TABLE votes
  ADD COLUMN FK_user_id int NULL;
ALTER TABLE votes
  ADD FOREIGN KEY (FK_user_id) REFERENCES users (user_id);
UNLOCK TABLES;
