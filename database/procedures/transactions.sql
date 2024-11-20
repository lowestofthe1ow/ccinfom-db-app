DELIMITER //

/* =========================================================================
   Generates a list of all the months for which a performer has a 'COMPLETE'
   performance.

   @params performer_id:    The ID of the performer
   @return month_on_record: The name of the month (e.g 'January')
           year_on_record:  The year
   ========================================================================= */
DROP PROCEDURE IF EXISTS hire //
CREATE PROCEDURE hire (
	IN first_name VARCHAR(255),
	IN last_name VARCHAR(255),
	IN contact_no DECIMAL(11, 0),
	IN position_id INT
)
-- ----------------------------------------------------------------------------
BEGIN

INSERT INTO
	staff (`first_name`, `last_name`, `contact_no`)
VALUES
	(first_name, last_name, contact_no);

INSERT INTO
	staff_position (
		`staff_id`,
		`position_id`,
		`start_date`,
		`end_date`
	)
VALUES
	(
		LAST_INSERT_ID (),
		position_id,
		DATE (NOW ()),
		NULL
	);

END //
DELIMITER;

-- Removing staff
DROP PROCEDURE IF EXISTS remove_staff;

DELIMITER //
CREATE PROCEDURE remove_staff (
	IN staff_id INT
) BEGIN

IF staff_id NOT IN (
	SELECT
		staff_id
	FROM
		staff
) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'No such staff';

ELSEIF (
	SELECT
		MAX(start_date)
	FROM
		staff_position sp
	WHERE
		sp.staff_id = staff_id
) >= DATE (NOW ()) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Staff must hold a position for at least a day';

ELSEIF (
	-- TODO: This is ugly
	SELECT
		sp.end_date
	FROM
		staff_position sp
	WHERE
		sp.staff_id = staff_id
		AND sp.start_date = (
			SELECT
				MAX(start_date)
			FROM
				staff_position sp
			WHERE
				sp.staff_id = staff_id
		)
) IS NOT NULL THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Staff is not active';

ELSE
UPDATE staff_position sp
SET
	sp.end_date = DATE (NOW ())
WHERE
	sp.end_date IS NULL
	AND sp.staff_id = staff_id;

END IF;

END //
DELIMITER;

-- Updating staff positions
DROP PROCEDURE IF EXISTS add_position;

DELIMITER //
CREATE PROCEDURE add_position (IN staff_id INT, IN position_id INT) BEGIN
 IF staff_id NOT IN (
	SELECT
		staff_id
	FROM
		staff
) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'No such staff';

ELSEIF (
	SELECT
		sp.position_id
	FROM
		staff_position sp
	WHERE
		sp.start_date = (
			SELECT
				MAX(sp2.start_date)
			FROM
				staff_position sp2
			WHERE
				sp2.staff_id = staff_id
		)
		AND sp.staff_id = staff_id
		AND sp.end_date IS NULL
) = position_id THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Staff is already assigned that position';

ELSEIF (
	SELECT
		MAX(start_date)
	FROM
		staff_position sp
	WHERE
		sp.staff_id = staff_id
) >= DATE (NOW ()) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Staff must hold a position for at least a day';

ELSE
UPDATE staff_position sp
SET
	sp.end_date = DATE (NOW ())
WHERE
	sp.end_date IS NULL
	AND sp.staff_id = staff_id;

INSERT INTO
	staff_position (
		`staff_id`,
		`position_id`,
		`start_date`,
		`end_date`
	)
VALUES
	(staff_id, position_id, DATE (NOW ()), NULL);

END IF;

END //
DELIMITER;

-- Add staff position types
DROP PROCEDURE IF EXISTS add_position_type;

DELIMITER //
CREATE PROCEDURE add_position_type (
	IN position_name VARCHAR(255),
	IN salary DECIMAL(10, 2)
) BEGIN
 IF salary < 500 THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Salary is under minimum wage';

ELSE
INSERT INTO
	position_type (`position_name`, `salary`)
VALUES
	(position_name, salary);

END IF;

END //
DELIMITER;

-- Renting out equipment
DROP PROCEDURE IF EXISTS rent_equipment;

DELIMITER //
CREATE PROCEDURE rent_equipment (
	IN performer_id INT,
	IN equipment_id INT,
	IN start_date DATE,
	IN end_date DATE
) BEGIN
 IF start_date >= end_date THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Invalid date range (equipment must be rented for at least a day)';

ELSEIF equipment_id NOT IN (
	SELECT
		equipment_id
	FROM
		equipment
) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'No such equipment';

ELSEIF (
	SELECT
		equipment_status
	FROM
		equipment e
	WHERE
		e.equipment_id = equipment_id
) <> 'UNDAMAGED' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Damaged or missing equipment cannot be rented out';

ELSEIF (
	SELECT
		COUNT(*)
	FROM
		equipment_rental er
	WHERE
		start_date < er.end_date
		AND end_date >= er.start_date
		AND er.equipment_id = equipment_id
		AND er.payment_status <> 'CANCELLED'
) > 0 THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Equipment unavailable on specified date range';

ELSE
INSERT INTO
	equipment_rental (
		`performer_id`,
		`equipment_id`,
		`start_date`,
		`end_date`,
		`equipment_status`,
		`payment_status`
	)
VALUES
	(
		performer_id,
		equipment_id,
		start_date,
		end_date,
		'PENDING',
		'NOT_PAID'
	);

END IF;

END //
DELIMITER;

-- Cancelling an equipment rental
DROP PROCEDURE IF EXISTS cancel_rental;

DELIMITER //
CREATE PROCEDURE cancel_rental (IN rental_id INT) BEGIN
 IF rental_id NOT IN (
	SELECT
		rental_id
	FROM
		equipment_rental
) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'No such rental';

ELSEIF (
	SELECT
		er.equipment_status
	FROM
		equipment_rental er
	WHERE
		er.rental_id = rental_id
) <> 'PENDING' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Rental has already been returned and evaluated';

ELSEIF (
	SELECT
		start_date
	FROM
		equipment_rental er
	WHERE
		er.rental_id = rental_id
) <= DATE (NOW ()) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Rental cannot be cancelled on the day it is to be rented out';

ELSE
UPDATE equipment_rental er
SET
	er.equipment_status = 'UNDAMAGED',
	er.payment_status = 'CANCELLED'
WHERE
	er.rental_id = rental_id;

END IF;

END //
DELIMITER;

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
) BEGIN
 IF rental_id NOT IN (
	SELECT
		rental_id
	FROM
		equipment_rental
) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'No such rental';

ELSEIF (
	SELECT
		er.equipment_status
	FROM
		equipment_rental er
	WHERE
		er.rental_id = rental_id
) <> 'PENDING' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Rental has already been returned and evaluated';

ELSE
UPDATE equipment_rental er
SET
	er.equipment_status = equipment_status
WHERE
	er.rental_id = rental_id;

UPDATE equipment e
SET
	e.equipment_status = equipment_status
WHERE
	e.equipment_id = (
		SELECT
			er.equipment_id
		FROM
			equipment_rental er
		WHERE
			er.rental_id = rental_id
	);

END IF;

END //
DELIMITER;

-- Accepting an audition 
DROP PROCEDURE IF EXISTS accept_audition;

DELIMITER //
CREATE PROCEDURE accept_audition (IN audition_id INT) BEGIN
 IF (
	SELECT
		a.audition_status
	FROM
		audition a
	WHERE
		a.audition_id = audition_id
) <> 'PENDING' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Audition is not waiting to be resolved';

ELSEIF (
	SELECT
		COUNT(performance_timeslot_id)
	FROM
		performance p
	WHERE
		p.performance_timeslot_id = (
			SELECT
				a.target_timeslot_id
			FROM
				audition a
			WHERE
				a.audition_id = audition_id
		)
		AND p.performance_status = 'PENDING'
) > 0 THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Performance slot is taken';

ELSE
UPDATE audition a
SET
	a.audition_status = 'PASSED'
WHERE
	a.audition_id = audition_id;

INSERT INTO
	performance (
		`performer_id`,
		`performance_timeslot_id`,
		`base_quota`,
		`performance_status`
	)
VALUES
	(
		(
			SELECT
				p.performer_id
			FROM
				audition a
				JOIN performer p ON a.performer_id = p.performer_id
			WHERE
				a.audition_id = audition_id
		),
		(
			SELECT
				a.target_timeslot_id
			FROM
				audition a
			WHERE
				a.audition_id = audition_id
		),
		'5000.00',
		'PENDING'
	);

END IF;

END //
DELIMITER;

-- Rejecting an audition
DROP PROCEDURE IF EXISTS reject_audition;

DELIMITER //
CREATE PROCEDURE reject_audition (IN audition_id INT) BEGIN
 IF (
	SELECT
		a.audition_status
	FROM
		audition a
	WHERE
		a.audition_id = audition_id
) <> 'PENDING' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Audition is not waiting to be resolved';

ELSE
UPDATE audition a
SET
	a.audition_status = 'REJECTED'
WHERE
	a.audition_id = audition_id;

END IF;

END //
DELIMITER;

-- Cancelling a performance
DROP PROCEDURE IF EXISTS cancel_performance;

DELIMITER //
CREATE PROCEDURE cancel_performance (IN performance_id INT) BEGIN
 IF (
	SELECT
		p.performance_status
	FROM
		performance p
	WHERE
		p.performance_id = performance_id
) <> 'PENDING' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Performance has already been completed or has already been cancelled';

ELSE
UPDATE performance p
SET
	p.performance_status = 'CANCELLED'
WHERE
	p.performance_id = performance_id;

END IF;

END //
DELIMITER;

-- Adding a performer
DROP PROCEDURE IF EXISTS add_performer;

DELIMITER //
CREATE PROCEDURE add_performer (
	IN performer_name VARCHAR(255),
	IN contact_last_name VARCHAR(255),
	IN contact_first_name VARCHAR(255),
	IN contact_no DECIMAL(11, 0)
) BEGIN

INSERT INTO
	performer (
		`performer_name`,
		`contact_last_name`,
		`contact_first_name`,
		`contact_no`
	)
VALUES
	(
		performer_name,
		contact_last_name,
		contact_first_name,
		contact_no
	);

END //
DELIMITER;

-- Adding a performance timeslot
DROP PROCEDURE IF EXISTS add_performance_timeslot;

DELIMITER //
CREATE PROCEDURE add_performance_timeslot (
	IN start_timestamp TIMESTAMP,
	IN end_timestamp TIMESTAMP
) BEGIN

INSERT INTO
	performance_timeslot (`start_timestamp`, `end_timestamp`)
VALUES
	(start_timestamp, end_timestamp);

END //
DELIMITER;

-- Scheduling an audition
DROP PROCEDURE IF EXISTS sched_audition;

DELIMITER //
CREATE PROCEDURE sched_audition (
	IN performer_id INT,
	IN target_timeslot_id INT,
	IN submission_link VARCHAR(255)
) BEGIN

INSERT INTO
	audition (
		`performer_id`,
		`target_timeslot_id`,
		`submission_link`,
		`audition_status`
	)
VALUES
	(
		performer_id,
		target_timeslot_id,
		submission_link,
		'PENDING'
	);

END //
DELIMITER;

DROP PROCEDURE IF EXISTS assign_staff;

DELIMITER //
CREATE PROCEDURE assign_staff (IN staff_id INT, IN performance_id INT) BEGIN
 IF (
	SELECT
		p.performance_status
	FROM
		performance p
	WHERE
		p.performance_id = performance_id
) <> 'PENDING' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Performance has already been completed or has already been cancelled';

ELSEIF staff_id IN (
	SELECT
		sa.staff_id
	FROM
		staff_assignment sa
	WHERE
		sa.performance_id = performance_id
) THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Performer is already assigned to this performance';

ELSE
INSERT INTO
	staff_assignment (`staff_id`, `performance_id`)
VALUES
	(staff_id, performance_id);

END IF;

END //
DELIMITER;

DROP PROCEDURE IF EXISTS record_performance_revenue;

DELIMITER //
CREATE PROCEDURE record_performance_revenue (
	IN performance_id INT,
	IN ticket_price DECIMAL(10, 2),
	IN tickets_sold INT,
	IN cut_percent DECIMAL(2, 2)
) BEGIN
 IF (
	SELECT
		p.performance_status
	FROM
		performance p
	WHERE
		p.performance_id = performance_id
) <> 'PENDING' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Performance has already been completed or has already been cancelled';

ELSE
UPDATE performance p
SET
	p.performance_status = 'COMPLETE'
WHERE
	p.performance_id = performance_id;

INSERT INTO
	performance_revenue (
		`performance_id`,
		`ticket_price`,
		`tickets_sold`,
		`cut_percent`
	)
VALUES
	(
		performance_id,
		ticket_price,
		tickets_sold,
		cut_percent
	);

END IF;

END //
DELIMITER;

-- Adding equipment
DROP PROCEDURE IF EXISTS add_equipment;

DELIMITER //
CREATE PROCEDURE add_equipment (
	IN equipment_type_id INT,
	IN equipment_name VARCHAR(255),
	IN rental_fee DECIMAL(10, 2)
) BEGIN

INSERT INTO
	equipment (
		`equipment_type_id`,
		`equipment_name`,
		`rental_fee`,
		`equipment_status`
	)
VALUES
	(
		equipment_type_id,
		equipment_name,
		rental_fee,
		'UNDAMAGED'
	);

END //
DELIMITER;

-- Adding equipment type
DROP PROCEDURE IF EXISTS add_equipment_type;

DELIMITER //
CREATE PROCEDURE add_equipment_type (IN equipment_type_name VARCHAR(255)) BEGIN

INSERT INTO
	equipment_type (`equipment_type_name`)
VALUES
	(equipment_type_name);

END //
DELIMITER;

-- Pay rental
DROP PROCEDURE IF EXISTS pay_rental;

DELIMITER //
CREATE PROCEDURE pay_rental (IN rental_id INT) BEGIN
 IF (
	SELECT
		er.payment_status
	FROM
		equipment_rental er
	WHERE
		er.rental_id = rental_id
) <> 'NOT_PAID' THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'Rental has already been paid or cancelled.';

ELSE
UPDATE equipment_rental er
SET
	er.payment_status = 'PAID'
WHERE
	er.rental_id = rental_id;

END IF;

END //
DELIMITER;

-- Change equipment status 
DROP PROCEDURE IF EXISTS change_equipment_status;

DELIMITER //
CREATE PROCEDURE change_equipment_status (IN equipment_id INT, IN new_status VARCHAR(10)) BEGIN
 DECLARE current_status VARCHAR(10);

DECLARE cancel_period INT;

SELECT
	e.equipment_status INTO current_status
FROM
	equipment e
WHERE
	e.equipment_id = equipment_id;

IF current_status = new_status THEN
SIGNAL SQLSTATE '45000' SET  MESSAGE_TEXT = 'The equipment is already in the specified status.';

END IF;

IF new_status = 'MIN_DMG' THEN
SET
	cancel_period = 7;

ELSEIF new_status = 'MAJ_DMG' THEN
SET
	cancel_period = 14;

ELSEIF new_status = 'MISSING' THEN
SET
	cancel_period = 0;

ELSE
SET
	cancel_period = NULL;

END IF;

IF cancel_period IS NOT NULL THEN
UPDATE equipment_rental er
SET
	er.payment_status = 'CANCELLED'
WHERE
	er.equipment_id = equipment_id
	AND (
		cancel_period = 0
		OR er.start_date BETWEEN NOW () AND DATE_ADD  (NOW (), INTERVAL cancel_period DAY)
	);

END IF;

UPDATE equipment e
SET
	e.equipment_status = new_status
WHERE
	e.equipment_id = equipment_id;

END //
DELIMITER;

-- Change equipment status 
DROP PROCEDURE IF EXISTS get_staff_assignments;

DELIMITER //
CREATE PROCEDURE get_staff_assignments (IN month_name VARCHAR(255), IN YEAR INT) BEGIN

SELECT
	sp.staff_id,
	CONCAT (s.first_name, ' ', s.last_name) AS staff_name,
	s.contact_no,
	SUM(pt.salary)
FROM
	staff s
	JOIN staff_position sp ON s.staff_id = sp.staff_id
	JOIN staff_assignment sa ON s.staff_id = sa.staff_id
	JOIN performance p ON sa.performance_id = p.performance_id
	JOIN performance_timeslot ps ON p.performance_timeslot_id = ps.performance_timeslot_id
	JOIN position_type pt ON sp.position_id = pt.position_id
WHERE
	(
		(
			sp.end_date IS NULL
			AND DATE (ps.start_timestamp) >= sp.start_date
		)
		OR (
			DATE (ps.start_timestamp) BETWEEN sp.start_date AND sp.end_date
		)
	)
	AND YEAR (ps.start_timestamp) = 2024
	AND MONTHNAME (ps.start_timestamp) = 'November'
GROUP BY
	sp.staff_id
ORDER BY
	staff_id;

END //
DELIMITER ;