ALTER TABLE users
  ADD COLUMN enabled BOOLEAN DEFAULT FALSE;
ALTER TABLE users
  ADD COLUMN activation_uuid VARCHAR(255) NULL;
-- update all existing users so they are enabled.
UPDATE users
SET users.enabled = TRUE;