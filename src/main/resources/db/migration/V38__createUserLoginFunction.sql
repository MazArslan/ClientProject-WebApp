DELIMITER //
CREATE FUNCTION totalUserLogin (user_id_in INT)
  RETURNS INT DETERMINISTIC

  BEGIN

    UPDATE users SET total_logins = total_logins + 1 WHERE user_id = user_id_in;

    RETURN (select total_login from users where user_id = user_id_in) ;
  END //

DELIMITER ;