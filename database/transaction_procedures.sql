-- Hiring staff

DROP PROCEDURE IF EXISTS hire;
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
		VALUES (LAST_INSERT_ID(), position_name, salary, DATE(NOW()), NULL);
END //
DELIMITER ;

-- Removing staff

DROP PROCEDURE IF EXISTS remove_staff;
DELIMITER //
CREATE PROCEDURE remove_staff (
	IN staff_id INT
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
    END IF;
END //
DELIMITER ;

-- Updating staff positions

DROP PROCEDURE IF EXISTS add_position;
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

-- Renting out equipment

DROP PROCEDURE IF EXISTS rent_equipment;
DELIMITER //
CREATE PROCEDURE rent_equipment (
	IN performer_id int,
	IN equipment_id int,
    IN start_date DATE,
    IN end_date DATE
)
BEGIN
	IF start_date >= end_date THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid date range (equipment must be rented for at least a day)';
	ELSEIF equipment_id NOT IN (SELECT id FROM equipment) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such equipment';
	ELSEIF (
		SELECT equipment_status
        FROM equipment e
        WHERE e.id = equipment_id
    ) <> 'UNDAMAGED' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Damaged or missing equipment cannot be rented out';
	ELSEIF (
		SELECT COUNT(*)
        FROM equipment_rental er
        WHERE start_date < er.end_date AND end_date >= er.start_date
			AND er.equipment_id = equipment_id
			AND er.payment_status <> 'CANCELLED'
    ) > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Equipment unavailable on specified date range';
	ELSE
		INSERT INTO equipment_rental
			(`performer_id`, `equipment_id`, `start_date`, `end_date`, `equipment_status`, `payment_status`)
        VALUES
			(performer_id, equipment_id, start_date, end_date, 'PENDING', 'NOT_PAID');
    END IF;
END //
DELIMITER ;

-- Cancelling an equipment rental

DROP PROCEDURE IF EXISTS cancel_rental;
DELIMITER //
CREATE PROCEDURE cancel_rental (
	IN rental_id INT
)
BEGIN
	IF rental_id NOT IN (SELECT rental_id FROM equipment_rental) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such rental';
	ELSEIF (
		SELECT er.equipment_status
        FROM equipment_rental er
        WHERE er.rental_id = rental_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rental has already been returned and evaluated';
	ELSEIF (
		SELECT start_date
        FROM equipment_rental er
        WHERE er.rental_id = rental_id
	) <= DATE(NOW()) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rental cannot be cancelled on the day it is to be rented out';
	ELSE
		UPDATE equipment_rental er
			SET er.equipment_status = 'UNDAMAGED', er.payment_status = 'CANCELLED'
            WHERE er.rental_id = rental_id;
    END IF;
END //
DELIMITER ;

-- Resolving an equipment rental

DROP PROCEDURE IF EXISTS resolve_equipment_status;
DELIMITER //
CREATE PROCEDURE resolve_equipment_status (
	IN rental_id INT,
    IN equipment_status ENUM (
		'UNDAMAGED',
        'MIN_DMG',
        'MAJ_DMG',
        'MISSING',
        'PENDING'
    )
)
BEGIN
	IF rental_id NOT IN (SELECT rental_id FROM equipment_rental) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such rental';
	ELSEIF (
		SELECT er.equipment_status
        FROM equipment_rental er
        WHERE er.rental_id = rental_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rental has already been returned and evaluated';
	ELSE
		UPDATE equipment_rental er
			SET er.equipment_status = equipment_status
            WHERE er.rental_id = rental_id;
		UPDATE equipment e
			SET e.equipment_status = equipment_status
            WHERE e.id = (
				SELECT er.equipment_id
                FROM equipment_rental er
                WHERE er.rental_id = rental_id
			);
    END IF;
END //
DELIMITER ;

-- Accepting an audition 

DROP PROCEDURE IF EXISTS accept_audition;
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
		) AND p.performance_status = 'PENDING'
    ) > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Performance slot is taken';
	ELSE
		UPDATE audition a
			SET a.audition_status = 'PASSED'
            WHERE a.id = audition_id;
		INSERT INTO performance (`performer_id`, `performance_timeslot_id`, `base_quota`, `performance_status`)
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
			), '5000.00', 'PENDING');
    END IF;
END //
DELIMITER ;

-- Rejecting an audition

DROP PROCEDURE IF EXISTS reject_audition;
DELIMITER //
CREATE PROCEDURE reject_audition (
	IN audition_id INT
)
BEGIN
	IF (
		SELECT a.audition_status
        FROM audition a
        WHERE a.id = audition_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Audition is not waiting to be resolved';
	ELSE
		UPDATE audition a
			SET a.audition_status = 'REJECTED'
            WHERE a.id = audition_id;
	END IF;
END //
DELIMITER ;

-- Cancelling a performance

DROP PROCEDURE IF EXISTS cancel_performance;
DELIMITER //
CREATE PROCEDURE cancel_performance (
	IN performance_id INT
)
BEGIN
	IF (
		SELECT p.performance_status
        FROM performance p
        WHERE p.id = performance_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Performance has already been completed or has already been cancelled';
	ELSE
		UPDATE performance p
			SET p.performance_status = 'CANCELLED'
            WHERE p.id = performance_id;
	END IF;
END //
DELIMITER ;