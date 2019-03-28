ALTER TABLE tutorial_completion
  ADD FOREIGN KEY (FK_user_id) REFERENCES users (user_id);

ALTER TABLE tutorial_completion
  ADD FOREIGN KEY (FK_tutorial_id) REFERENCES tutorials (tutorial_id);

