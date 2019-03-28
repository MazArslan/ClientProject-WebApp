## this is the same as the function 'totalUserLogin' however its a trigger

DELIMITER //
CREATE TRIGGER userLoginAdd after INSERT ON timespent FOR EACH ROW
  BEGIN
    SET @FK_user = (SELECT FK_user_id from timespent where fk_user_id = new.fk_user_id and last_activity is null);

    UPDATE users set total_logins = total_logins + 1 where users.user_id = @FK_user;
  END//
DELIMITER ;