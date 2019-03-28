DELETE FROM votes
WHERE FK_user_id IS NOT NULL;
DELETE FROM users
WHERE user_id > 2;