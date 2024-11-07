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
		UPDATE staff_position sp
			SET sp.end_date = DATE(NOW())
			WHERE sp.end_date IS NULL AND sp.staff_id = staff_id;
		INSERT INTO staff_position (`staff_id`, `staff_position_name`, `staff_salary`, `start_date`, `end_date`)
			VALUES (staff_id, position_name, salary, DATE(NOW()), NULL);
	END IF;
END //
DELIMITER ;

-- Accepting an audition 

DELIMITER //
CREATE PROCEDURE accept_audition (
	IN audition_id INT
)
BEGIN
	IF (
		SELECT a.audition_status
        FROM audition a
        WHERE a.id = audition_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Audition is not waiting to be resolved';
	ELSEIF (
		SELECT COUNT(performance_timeslot_id)
		FROM performance p
		WHERE p.performance_timeslot_id = (
			SELECT a.target_timeslot_id
			FROM audition a
			WHERE a.id = audition_id
		)
    ) > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Performance slot is taken';
	ELSE
		UPDATE audition a
			SET a.audition_status = 'PASSED_PENDING'
            WHERE a.id = audition_id;
		INSERT INTO performance (`performer_id`, `performance_timeslot_id`, `base_quota`)
			VALUES ((
				SELECT p.id
                FROM audition a
                JOIN performer p
                ON a.performer_id = p.id
                WHERE a.id = audition_id
            ), (
				SELECT a.target_timeslot_id
				FROM audition a
				WHERE a.id = audition_id
			), '5000.00');
    END IF;
END //
DELIMITER ;