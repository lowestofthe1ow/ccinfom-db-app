-- Fetch position data

DROP PROCEDURE IF EXISTS get_positions;
DELIMITER //
CREATE PROCEDURE get_positions ()
BEGIN
	SELECT * FROM position_type;
END //
DELIMITER ;

-- Fetch audition data

DROP PROCEDURE IF EXISTS get_auditions;
DELIMITER //
CREATE PROCEDURE get_auditions ()
BEGIN
	SELECT a.audition_id, p.performer_name, a.submission_link, pt.timeslot_date, pt.start_time,
		CONCAT(p.contact_first_name, ' ', p.contact_last_name) AS full_name,
		p.contact_no AS contact_no
    FROM audition a
    LEFT JOIN performer p
		ON p.performer_id = a.performer_id
	LEFT JOIN performance_timeslot pt
		ON a.target_timeslot_id = pt.performance_timeslot_id
    WHERE a.audition_status = 'PENDING';
END //
DELIMITER ;

<<<<<<< HEAD
-- Fetch active staff data

DROP PROCEDURE IF EXISTS get_active_staff;
DELIMITER //
CREATE PROCEDURE get_active_staff ()
BEGIN
	SELECT sp.staff_id, CONCAT(first_name, ' ', last_name) AS full_name, contact_no, pt.position_name, pt.salary
	FROM staff s
	JOIN staff_position sp
	ON s.staff_id = sp.staff_id
    JOIN position_type pt
    ON sp.position_id = pt.position_id
	WHERE sp.start_date = (
		SELECT MAX(start_date)
        FROM staff_position sp2
		WHERE sp.staff_id = sp2.staff_id
	) AND sp.end_date IS NULL;
END //
DELIMITER ;

-- Fetch staff data

DROP PROCEDURE IF EXISTS get_staff;
DELIMITER //
CREATE PROCEDURE get_staff ()
BEGIN
	SELECT s.staff_id, CONCAT(first_name, ' ', last_name) AS full_name, contact_no, IFNULL(pt.position_name, 'N/A'), IFNULL(pt.salary, 'N/A')
	FROM staff s
	LEFT JOIN staff_position sp
	ON s.staff_id = sp.staff_id
    AND sp.start_date = (
		SELECT MAX(start_date)
        FROM staff_position sp2
		WHERE sp.staff_id = sp2.staff_id
	) AND sp.end_date IS NULL
    LEFT JOIN position_type pt
    ON sp.position_id = pt.position_id;
=======
-- Fetch staff data

DROP PROCEDURE IF EXISTS get_staff;
DELIMITER //
CREATE PROCEDURE get_staff ()
BEGIN
	SELECT sp.staff_id, CONCAT(first_name, ' ', last_name) AS full_name, contact_no, staff_position_name
	FROM staff s
	JOIN staff_position sp
	ON s.staff_id = sp.staff_id
	WHERE sp.start_date = (
		SELECT MAX(start_date)
        FROM staff_position sp2
		WHERE sp.staff_id = sp2.staff_id
	) AND sp.end_date IS NULL;
>>>>>>> branch 'main' of git@github.com:lowestofthe1ow/ccinfom-db-app.git
END //
DELIMITER ;

-- Hiring staff

DROP PROCEDURE IF EXISTS hire;
DELIMITER //
CREATE PROCEDURE hire (
	IN first_name VARCHAR(255),
    IN last_name VARCHAR(255),
    IN contact_no DECIMAL(11, 0),
    IN position_id INT
)
BEGIN
	INSERT INTO staff (`first_name`, `last_name`, `contact_no`) 
		VALUES (first_name, last_name, contact_no);
	INSERT INTO staff_position (`staff_id`, `position_id`, `start_date`, `end_date`)
		VALUES (LAST_INSERT_ID(), position_id, DATE(NOW()), NULL);
END //
DELIMITER ;

-- Removing staff

DROP PROCEDURE IF EXISTS remove_staff;
DELIMITER //
CREATE PROCEDURE remove_staff (
	IN staff_id INT
)
BEGIN
	IF staff_id NOT IN (SELECT staff_id FROM staff) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such staff';
	ELSEIF (
		SELECT MAX(start_date) 
		FROM staff_position sp
		WHERE sp.staff_id = staff_id
	) >= DATE(NOW()) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Staff must hold a position for at least a day';
	ELSEIF (
    -- TODO: This is ugly
		SELECT sp.end_date
        FROM staff_position sp
        WHERE sp.staff_id = staff_id AND sp.start_date = (
			SELECT MAX(start_date) 
			FROM staff_position sp
			WHERE sp.staff_id = staff_id
		)
    ) IS NOT NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Staff is not active';
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
    IN position_id INT
)
BEGIN
	IF staff_id NOT IN (SELECT staff_id FROM staff) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such staff';
	ELSEIF (
		SELECT sp.position_id
        FROM staff_position sp
        WHERE sp.start_date = (
			SELECT MAX(sp2.start_date) 
			FROM staff_position sp2
			WHERE sp2.staff_id = staff_id
        ) AND sp.staff_id = staff_id AND sp.end_date IS NULL
    ) = position_id THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Staff is already assigned that position';
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
		INSERT INTO staff_position (`staff_id`, `position_id`, `start_date`, `end_date`)
			VALUES (staff_id, position_id, DATE(NOW()), NULL);
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
	ELSEIF equipment_id NOT IN (SELECT equipment_id FROM equipment) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No such equipment';
	ELSEIF (
		SELECT equipment_status
        FROM equipment e
        WHERE e.equipment_id = equipment_id
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
            WHERE e.equipment_id = (
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
        WHERE a.audition_id = audition_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Audition is not waiting to be resolved';
	ELSEIF (
		SELECT COUNT(performance_timeslot_id)
		FROM performance p
		WHERE p.performance_timeslot_id = (
			SELECT a.target_timeslot_id
			FROM audition a
			WHERE a.audition_id = audition_id
		) AND p.performance_status = 'PENDING'
    ) > 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Performance slot is taken';
	ELSE
		UPDATE audition a
			SET a.audition_status = 'PASSED'
            WHERE a.audition_id = audition_id;
		INSERT INTO performance (`performer_id`, `performance_timeslot_id`, `base_quota`, `performance_status`)
			VALUES ((
				SELECT p.performer_id
                FROM audition a
                JOIN performer p
                ON a.performer_id = p.performer_id
                WHERE a.audition_id = audition_id
            ), (
				SELECT a.target_timeslot_id
				FROM audition a
				WHERE a.audition_id = audition_id
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
        WHERE a.audition_id = audition_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Audition is not waiting to be resolved';
	ELSE
		UPDATE audition a
			SET a.audition_status = 'REJECTED'
            WHERE a.audition_id = audition_id;
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
        WHERE p.performance_id = performance_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Performance has already been completed or has already been cancelled';
	ELSE
		UPDATE performance p
			SET p.performance_status = 'CANCELLED'
            WHERE p.performance_id = performance_id;
	END IF;
END //
DELIMITER ;

-- Adding a performer

DROP PROCEDURE IF EXISTS add_performer;
DELIMITER //
CREATE PROCEDURE add_performer (
	IN performer_name VARCHAR(255),
    IN contact_last_name VARCHAR(255),
    IN contact_first_name VARCHAR(255),
    IN contact_no DECIMAL(11, 0)
)
BEGIN
	INSERT INTO performer (`performer_name`, `contact_last_name`, `contact_first_name`, `contact_no`) 
		VALUES (performer_name, contact_last_name, contact_first_name, contact_no);
END //
DELIMITER ;

-- Adding a performance timeslot

DROP PROCEDURE IF EXISTS add_performance_timeslot;
DELIMITER //
CREATE PROCEDURE add_performance_timeslot (
	IN start_timestamp TIMESTAMP,
    IN end_timestamp TIMESTAMP
)
BEGIN
	INSERT INTO performance_timeslot (`start_timestamp`, `end_timestamp`) 
		VALUES (start_timestamp, end_timestamp);
END //
DELIMITER ;

-- Scheduling an audition

DROP PROCEDURE IF EXISTS sched_audition;
DELIMITER //
CREATE PROCEDURE sched_audition (
	IN performer_id INT,
	IN target_timeslot_id INT,
    IN submission_link VARCHAR(255)
)
BEGIN
	INSERT INTO audition (`performer_id`, `target_timeslot_id`, `submission_link`, `audition_status`)
			VALUES (performer_id, target_timeslot_id, submission_link, 'PENDING');
END //
DELIMITER ;

-- Assigning staff to performance

DROP PROCEDURE IF EXISTS assign_staff;
DELIMITER //
CREATE PROCEDURE assign_staff (
	IN staff_id INT,
    IN performance_id INT
)
BEGIN
	IF (
		SELECT p.performance_status
        FROM performance p
        WHERE p.performance_id = performance_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Performance has already been completed or has already been cancelled';
	ELSE
		INSERT INTO staff_assignment (`staff_id`, `performance_id`)
			VALUES (staff_id, performance_id);
	END IF;
END //
DELIMITER ;

-- Recording a performance’s generated revenue (after the performance)

DROP PROCEDURE IF EXISTS record_performance_revenue;
DELIMITER //
CREATE PROCEDURE record_performance_revenue (
	IN performance_id INT,
    IN ticket_price DECIMAL,
    IN tickets_sold INT,
    IN cut_percent DECIMAL
)
BEGIN
	IF (
		SELECT p.performance_status
        FROM performance p
        WHERE p.performance_id = performance_id
	) <> 'PENDING' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Performance has already been completed or has already been cancelled';
	ELSE
		UPDATE performance p
			SET p.performance_status = 'COMPLETE'
            WHERE p.performance_id = performance_id;
		INSERT INTO performance_revenue (`performance_id`, `ticket_price`, `tickets_sold`, `cut_percent`)
			VALUES (performance_id, ticket_price, tickets_sold, cut_percent);
	END IF;
END //
DELIMITER ;

<<<<<<< HEAD
DROP PROCEDURE IF EXISTS performer_report_day;

DELIMITER //
CREATE PROCEDURE performer_report_day(
    IN performer_id INT,
    IN aday DATE
)
BEGIN
    SELECT 
        pf.performer_name,
        pts.timeslot_date,
        SUM((pr.ticket_price * pr.tickets_sold - p.base_quota)* (1 - pr.cut_percent)) AS earning_day
    FROM 
        performance_revenue pr
    JOIN 
        performance p ON pr.performance_id = p.performance_id
    JOIN 
        performer pf ON p.performer_id = pf.performer_id
    JOIN 
        performance_timeslot pts ON pts.performance_timeslot_id = p.performance_timeslot_id
    WHERE 
        pf.performer_id = performer_id
        AND DATE(pts.timeslot_date) = aday
        AND p.performance_status = 'COMPLETE'
    GROUP BY 
        pf.performer_name, 
        pts.timeslot_date;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS cut_report_month;

DELIMITER //
CREATE PROCEDURE cut_report_month(
    IN month INT,
    IN year INT
)
BEGIN
    SELECT 
        SUM((pr.ticket_price * pr.tickets_sold - p.base_quota) * (1-pr.cut_percent) ) AS monthCut
    FROM 
        performance_revenue pr
    JOIN 
        performance p ON pr.performance_id = p.performance_id
    JOIN 
        performance_timeslot pts ON pts.performance_timeslot_id = p.performance_timeslot_id
    WHERE 
        MONTH(pts.timeslot_date) = month
        AND YEAR(pts.timeslot_date) = year;
END //

=======
-- Adding equipment
DROP PROCEDURE IF EXISTS add_equipment;
DELIMITER //
CREATE PROCEDURE add_equipment (
	IN equipment_type_id INT,
    IN equipment_name VARCHAR(255),
    IN rental_fee DECIMAL(10, 2),
    IN equipment_status VARCHAR(255)
)
BEGIN
	INSERT INTO performance_timeslot (`equipment_type_id`, `equipment_name`, `rental_fee`, `equipment_status`) 
		VALUES (equipment_type_id, equipment_name, rental_fee, equipment_status);
END //
DELIMITER ;

-- Adding equipment type
DROP PROCEDURE IF EXISTS add_equipment_type;
DELIMITER //
CREATE PROCEDURE add_equipment_type (
    IN equipment_type_name VARCHAR(255)
)
BEGIN
	INSERT INTO performance_timeslot (`equipment_type_name`) 
		VALUES (equipment_type_name);
END //
>>>>>>> branch 'main' of git@github.com:lowestofthe1ow/ccinfom-db-app.git
DELIMITER ;