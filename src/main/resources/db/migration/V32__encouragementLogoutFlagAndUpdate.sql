ALTER TABLE encouragement
  ADD COLUMN logout_button BOOLEAN DEFAULT FALSE;
UPDATE encouragement
SET encouragement.logout_button = TRUE
WHERE encouragement_id = 3;