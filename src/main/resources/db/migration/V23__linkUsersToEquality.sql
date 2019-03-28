ALTER TABLE equality_diversity
    ADD COLUMN FK_user_id int ;
ALTER TABLE equality_diversity
    ADD FOREIGN KEY (FK_user_id) REFERENCES users (user_id);