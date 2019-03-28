DELIMITER //
CREATE PROCEDURE remove_user_tutorial_completion(user_id_input INT, tutorial_id_given INT)
  BEGIN
    DELETE FROM tutorial_completion
    WHERE FK_user_id = user_id_input AND FK_tutorial_id = tutorial_id_given;
  END//
DELIMITER ;