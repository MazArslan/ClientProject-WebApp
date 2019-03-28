ALTER TABLE `clientproject`.`users`
  ADD COLUMN `total_logins` INT NULL DEFAULT 0 AFTER `FK_role_id`;
