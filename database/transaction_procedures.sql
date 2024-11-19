DROP PROCEDURE IF EXISTS get_months_with_performances_by;
DELIMITER //
CREATE PROCEDURE get_months_with_performances_by (
	IN performer_id INT
)
BEGIN
	SELECT 
		MONTHNAME(pt.start_timestamp) AS month_on_record,
        YEAR(pt.start_timestamp) AS year_on_record
	FROM performance p
	JOIN performance_timeslot pt
		ON p.performance_timeslot_id = pt.performance_timeslot_id
	WHERE p.performance_status = 'COMPLETE' AND p.performer_id = performer_id
	GROUP BY month_on_record, year_on_record;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS get_months_on_record;
DELIMITER //
CREATE PROCEDURE get_months_on_record ()
BEGIN
	SELECT 
		MONTHNAME(pt.start_timestamp) AS month_on_record,
        YEAR(pt.start_timestamp) AS year_on_record
	FROM performance p
	JOIN performance_timeslot pt
		ON p.performance_timeslot_id = pt.performance_timeslot_id
	WHERE p.performance_status = 'COMPLETE'
	GROUP BY month_on_record, year_on_record;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS get_performances_in_month;
DELIMITER //
CREATE PROCEDURE get_performances_in_month (
	month_name VARCHAR(255),
    year_name INT
)
BEGIN
	SELECT 
		p.performance_id,
		pf.performer_name,
		pt.start_timestamp,
        (CASE
				WHEN pr.ticket_price * pr.tickets_sold > p.base_quota
					THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * pr.cut_percent
				ELSE 0
			END) AS monthCut
	FROM performance p
	JOIN performance_timeslot pt
	ON p.performance_timeslot_id = pt.performance_timeslot_id
	JOIN performer pf
	ON p.performer_id = pf.performer_id
    JOIN performance_revenue pr
    ON p.performance_id = pr.performance_id
    WHERE MONTHNAME(pt.start_timestamp) = month_name
    AND YEAR(pt.start_timestamp) = year_name
    AND p.performance_status = 'COMPLETE'
    ORDER BY pt.start_timestamp DESC;
END //
DELIMITER ;

-- Fetch performance data

DROP PROCEDURE IF EXISTS get_performances;
DELIMITER //
CREATE PROCEDURE get_performances ()
BEGIN
	SELECT 
		p.performance_id,
		pr.performer_name,
		pt.start_timestamp,
        p.performance_status
	FROM performance p
	JOIN performance_timeslot pt
	ON p.performance_timeslot_id = pt.performance_timeslot_id
	JOIN performer pr
	ON p.performer_id = pr.performer_id
    ORDER BY pt.start_timestamp DESC;
END //
DELIMITER ;

-- Fetch equipment type data

DROP PROCEDURE IF EXISTS get_equipment_types;
DELIMITER //
CREATE PROCEDURE get_equipment_types ()
BEGIN
	SELECT * FROM equipment_type;
END //
DELIMITER ;

-- Fetch timeslot data

DROP PROCEDURE IF EXISTS get_timeslots;
DELIMITER //
CREATE PROCEDURE get_timeslots ()
BEGIN
	SELECT * FROM performance_timeslot pt
    WHERE (
		SELECT COUNT(*)
		FROM performance p
        WHERE p.performance_timeslot_id = pt.performance_timeslot_id
        AND p.performance_status <> 'CANCELLED'
    ) = 0;
END //
DELIMITER ;

-- Fetch performer data

DROP PROCEDURE IF EXISTS get_performers;
DELIMITER //
CREATE PROCEDURE get_performers ()
BEGIN
	SELECT
		p.performer_id,
        p.performer_name,
        CONCAT(p.contact_first_name, ' ', p.contact_last_name) AS contact_person,
        p.contact_no
    FROM performer p;
END //
DELIMITER ;

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
	SELECT a.audition_id, p.performer_name, a.submission_link, pt.start_timestamp,
		CONCAT(p.contact_first_name, ' ', p.contact_last_name) AS full_name,
		p.contact_no AS contact_no,
        a.audition_status
    FROM audition a
    LEFT JOIN performer p
		ON p.performer_id = a.performer_id
	LEFT JOIN performance_timeslot pt
		ON a.target_timeslot_id = pt.performance_timeslot_id;
    -- WHERE a.audition_status = 'PENDING';
END //
DELIMITER ;

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
END //
DELIMITER ;

-- Fetch undamaged equipment data

DROP PROCEDURE IF EXISTS get_undamaged_equipment;
DELIMITER //
CREATE PROCEDURE get_undamaged_equipment ()
BEGIN
	SELECT e.equipment_id, e.equipment_name, et.equipment_type_name, e.rental_fee
    FROM equipment e
    JOIN equipment_type et
		ON e.equipment_type_id = et.equipment_type_id	
    WHERE e.equipment_status = 'UNDAMAGED';
END //
DELIMITER ;

-- Fetch equipment rental data

DROP PROCEDURE IF EXISTS get_equipment_rentals;
DELIMITER //
CREATE PROCEDURE get_equipment_rentals ()
BEGIN
	SELECT er.rental_id, e.equipment_name, p.performer_name, er.start_date, er.end_date,
		er.equipment_status
    FROM equipment_rental er
    JOIN equipment e
		ON er.equipment_id = e.equipment_id
    JOIN performer p
		ON er.performer_id = p.performer_id
	WHERE er.equipment_status = 'PENDING';
END //
DELIMITER ;

-- Fetch unpaid equipment rental data

DROP PROCEDURE IF EXISTS get_unpaid_rentals;
DELIMITER //
CREATE PROCEDURE get_unpaid_rentals ()
BEGIN
	SELECT er.rental_id, e.equipment_name, p.performer_name, er.start_date, er.end_date,
		er.payment_status
    FROM equipment_rental er
    JOIN equipment e
		ON er.equipment_id = e.equipment_id
    JOIN performer p
		ON er.performer_id = p.performer_id
	WHERE er.payment_status = 'NOT_PAID' AND er.equipment_status <> 'PENDING';
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

-- Add staff position types

DROP PROCEDURE IF EXISTS add_position_type;
DELIMITER //
CREATE PROCEDURE add_position_type (
	IN position_name VARCHAR(255),
    IN salary DECIMAL(10, 2)
)
BEGIN
	IF salary < 500 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Salary is under minimum wage';
	ELSE
		INSERT INTO position_type (`position_name`, `salary`)
		VALUES (position_name, salary);
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
    IN ticket_price DECIMAL(10,2),
    IN tickets_sold INT,
    IN cut_percent DECIMAL(2,2)
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

-- TODO: ...base quotas? Idk
DROP PROCEDURE IF EXISTS performer_report_day;

DELIMITER //
CREATE PROCEDURE performer_report_day(
    IN performer_id INT,
    IN aday DATE
)
BEGIN
    SELECT 
        pf.performer_name,
        DATE(pts.start_timestamp) AS performance_day,
        SUM(pr.ticket_price * pr.tickets_sold) AS sales_on_day,
        SUM(CASE
				WHEN pr.ticket_price * pr.tickets_sold > p.base_quota
					THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * (1 - pr.cut_percent)
                ELSE 0
			END) AS performer_profit_on_day,
		SUM(CASE
				WHEN pr.ticket_price * pr.tickets_sold < p.base_quota
					THEN p.base_quota - pr.ticket_price * pr.tickets_sold
				ELSE 0
			END) AS performer_debt_on_day,
		SUM(CASE
				WHEN pr.ticket_price * pr.tickets_sold > p.base_quota
					THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * pr.cut_percent
				ELSE 0
			END) AS livehouse_profit_on_day
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
        AND DATE(pts.start_timestamp) = aday
        AND p.performance_status = 'COMPLETE'
    GROUP BY 
        pf.performer_name, 
        performance_day;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS cut_report_month;

DELIMITER //
CREATE PROCEDURE cut_report_month(
    IN month_name VARCHAR(255),
    IN year INT
)
BEGIN
    SELECT 
        SUM(CASE
				WHEN pr.ticket_price * pr.tickets_sold > p.base_quota
					THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * pr.cut_percent
				ELSE 0
			END) AS monthCut
    FROM 
        performance_revenue pr
    JOIN 
        performance p ON pr.performance_id = p.performance_id
    JOIN 
        performance_timeslot pts ON pts.performance_timeslot_id = p.performance_timeslot_id
    WHERE 
        MONTHNAME(pts.start_timestamp) = month_name
        AND YEAR(pts.end_timestamp) = year;
END //

DELIMITER ;

-- Adding equipment
DROP PROCEDURE IF EXISTS add_equipment;
DELIMITER //
CREATE PROCEDURE add_equipment (
	IN equipment_type_id INT,
    IN equipment_name VARCHAR(255),
    IN rental_fee DECIMAL(10, 2)
)
BEGIN
	INSERT INTO equipment (`equipment_type_id`, `equipment_name`, `rental_fee`, `equipment_status`) 
		VALUES (equipment_type_id, equipment_name, rental_fee, 'UNDAMAGED');
END //
DELIMITER ;

-- Adding equipment type
DROP PROCEDURE IF EXISTS add_equipment_type;
DELIMITER //
CREATE PROCEDURE add_equipment_type (
    IN equipment_type_name VARCHAR(255)
)
BEGIN
	INSERT INTO equipment_type (`equipment_type_name`) 
		VALUES (equipment_type_name);
END //
DELIMITER ;

-- Pay rental
DROP PROCEDURE IF EXISTS pay_rental;
DELIMITER //
CREATE PROCEDURE pay_rental (
	IN rental_id int
)
BEGIN
	IF (
		SELECT er.payment_status
        FROM equipment_rental er
        WHERE er.rental_id = rental_id
	) <> 'NOT_PAID' THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rental has already been paid or cancelled.';
	ELSE
		UPDATE equipment_rental er
			SET er.payment_status = 'PAID'
            WHERE er.rental_id = rental_id;
	END IF;
END //
DELIMITER ;

-- Get schedule for a week based on NOw(). 

DROP PROCEDURE IF EXISTS get_schedule;
DELIMITER // 
CREATE PROCEDURE get_schedule()
BEGIN 
	SELECT 	pf.performer_name,
			DAYNAME(pts.start_timestamp) AS day_name,
			pts.start_timestamp,
			pts.end_timestamp
            
	FROM 
		performance p
	JOIN 
		performance_timeslot pts ON p.performance_timeslot_id = pts.performance_timeslot_id
	JOIN 
		performer pf ON p.performer_id = pf.performer_id
	WHERE 
		p.performance_status = 'PENDING'
		AND YEARWEEK(pts.start_timestamp) = YEARWEEK(CURDATE())
	ORDER BY
		pts.start_timestamp;
END //
DELIMITER ;


-- Rental report per month
DROP PROCEDURE IF  EXISTS equipment_rental_report;
DELIMITER //

CREATE PROCEDURE equipment_rental_report(
    IN performer_id INT,
    IN month_name VARCHAR(255),
    IN year INT
)
BEGIN
    SELECT 
        e.equipment_name,
        COUNT(er.rental_id) AS rentals_month,
        SUM(e.rental_fee) AS rental_costs_month
    FROM 
        equipment_rental er
    JOIN 
        equipment e ON er.equipment_id = e.equipment_id
    WHERE 
        MONTHNAME(er.start_date) = month_name
        AND YEAR(er.start_date) = year
		AND er.payment_status = 'PAID'
    GROUP BY 
        e.equipment_name
    ORDER BY 
        rental_costs_month DESC;
END //

DELIMITER ;


-- performer report month

DROP PROCEDURE IF EXISTS performer_report_month;
DELIMITER //
CREATE PROCEDURE performer_report_month(
    IN performer_id INT,
    IN month_name VARCHAR(255),
    IN year INT
)
BEGIN
    SELECT 
        pf.performer_name,
        DATE_FORMAT(pts.start_timestamp, '%Y-%m') AS performance_month,
        SUM(pr.ticket_price * pr.tickets_sold) AS total_sales,
        SUM(CASE
				WHEN pr.ticket_price * pr.tickets_sold > p.base_quota
					THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * (1 - pr.cut_percent)
                ELSE 0
			END) AS performer_profit_month,
		SUM(CASE
				WHEN pr.ticket_price * pr.tickets_sold < p.base_quota
					THEN p.base_quota - pr.ticket_price * pr.tickets_sold
				ELSE 0
			END) AS performer_debt_month,
		SUM(CASE
				WHEN pr.ticket_price * pr.tickets_sold > p.base_quota
					THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * pr.cut_percent
				ELSE 0
			END) AS livehouse_profit_month
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
		AND MONTHNAME(pts.start_timestamp) = month_name
        AND YEAR(pts.end_timestamp) = year
        AND p.performance_status = 'COMPLETE'
    GROUP BY 
        pf.performer_name, 
        performance_month;
END //
DELIMITER ;


-- get staff assignments

DROP PROCEDURE IF EXISTS get_staff_assignments;
DELIMITER //

CREATE PROCEDURE get_staff_assignments(
    IN month_name VARCHAR(255),
    IN year INT
)
BEGIN
    SELECT
    sp.staff_id,
    CONCAT(s.first_name, ' ', s.last_name) AS staff_name,
    s.contact_no,
    SUM(pt.salary)
	FROM staff s
	JOIN staff_position sp
		ON s.staff_id = sp.staff_id
	JOIN staff_assignment sa
		ON s.staff_id = sa.staff_id
	JOIN performance p
		ON sa.performance_id = p.performance_id
	JOIN performance_timeslot ps
		ON p.performance_timeslot_id = ps.performance_timeslot_id
	JOIN position_type pt
		ON sp.position_id = pt.position_id
	WHERE ((sp.end_date IS NULL AND DATE(ps.start_timestamp) >= sp.start_date)
		OR (DATE(ps.start_timestamp) BETWEEN sp.start_date AND sp.end_date))
		AND YEAR(ps.start_timestamp) = year
		AND MONTHNAME(ps.start_timestamp) = month_name
	GROUP BY sp.staff_id
	ORDER BY staff_id;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS change_equipment_status;
DELIMITER //
CREATE PROCEDURE change_equipment_status(
    IN equipment_id INT,
    IN new_status VARCHAR(10)
)
BEGIN
    DECLARE current_status VARCHAR(10);
    DECLARE cancel_period INT;

    SELECT e.equipment_status INTO current_status
    FROM equipment e
    WHERE e.equipment_id = equipment_id;

    IF current_status = new_status THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'The equipment is already in the specified status.';
    END IF;

    IF new_status = 'MIN_DMG' THEN
        SET cancel_period = 7;
    ELSEIF new_status = 'MAJ_DMG' THEN
        SET cancel_period = 14;
    ELSEIF new_status = 'MISSING' THEN
        SET cancel_period = 0; 
    ELSE
        SET cancel_period = NULL; 
    END IF;

    IF cancel_period IS NOT NULL THEN
        UPDATE equipment_rental er
        SET er.payment_status = 'CANCELLED'
        WHERE er.equipment_id = equipment_id
        AND (
            cancel_period = 0 OR 
            er.start_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL cancel_period DAY)
        );
        
    END IF;

	UPDATE equipment e
	SET e.equipment_status = new_status
	WHERE e.equipment_id = equipment_id;
END //

DELIMITER ;