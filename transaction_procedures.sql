-- Hiring staff

DELIMITER //
CREATE PROCEDURE hire (
	IN first_name VARCHAR(255),
    IN last_name VARCHAR(255),
    IN contact_no DECIMAL(11, 0),
    IN position_name VARCHAR(255),
    IN salary DECIMAL(10, 2)
)
BEGIN
	INSERT INTO staff (`first_name`, `last_name`, `contact_no`) 
		VALUES (first_name, last_name, contact_no);
	INSERT INTO staff_position (`staff_id`, `staff_position_name`, `staff_salary`, `start_date`, `end_date`)
		VALUES (LAST_INSERT_ID(), position_name, salary, DATE(NOW()), '0000-00-00');
END //
DELIMITER ;

-- Updating staff positions

DELIMITER //
CREATE PROCEDURE add_position (
	IN staff_id INT,
    IN position_name VARCHAR(255),
    IN salary DECIMAL(10, 2)
)
BEGIN
	IF staff_id NOT IN (SELECT id FROM staff) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such staff';
	ELSEIF (
		SELECT MAX(start_date) 
		FROM staff_position sp
		WHERE sp.staff_id = staff_id
	) >= DATE(NOW()) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Staff must hold a position for at least a day';
	ELSE
		UPDATE staff_position
			SET end_date = DATE(NOW())
			WHERE end_date IS NULL;
		INSERT INTO staff_position (`staff_id`, `staff_position_name`, `staff_salary`, `start_date`, `end_date`)
			VALUES (staff_id, position_name, salary, DATE(NOW()), '0000-00-00');
	END IF;
END //
DELIMITER ;