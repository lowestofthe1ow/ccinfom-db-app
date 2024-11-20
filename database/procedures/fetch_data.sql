DELIMITER //

/* =========================================================================
   Generates a list of all the months for which a performer has a 'COMPLETE'
   performance.

   @params performer_id:    The ID of the performer
   @return month_on_record: The name of the month (e.g 'January')
           year_on_record:  The year
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_months_with_performances_by //
CREATE PROCEDURE get_months_with_performances_by (
    IN performer_id INT
)
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	MONTHNAME (pt.start_timestamp) AS month_on_record,
	YEAR (pt.start_timestamp) AS year_on_record
FROM
	performance p
	JOIN performance_timeslot pt ON p.performance_timeslot_id = pt.performance_timeslot_id
WHERE
	p.performance_status = 'COMPLETE'
	AND p.performer_id = performer_id
GROUP BY
	month_on_record,
	year_on_record;
END //

/* =========================================================================
   Generates a list of all the months for which there are any recorded
   'COMPLETE' performances.

   @return month_on_record: The name of the month (e.g 'January')
           year_on_record:  The year
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_months_on_record //
CREATE PROCEDURE get_months_on_record ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	MONTHNAME (pt.start_timestamp) AS month_on_record,
	YEAR (pt.start_timestamp) AS year_on_record
FROM
	performance p
	JOIN performance_timeslot pt ON p.performance_timeslot_id = pt.performance_timeslot_id
WHERE
	p.performance_status = 'COMPLETE'
GROUP BY
	month_on_record,
	year_on_record;
END //

/* =========================================================================
   Generates a list of all the performances for a given month in a year.

   @params month_on_record: The name of the month (e.g 'January')
           year_on_record:  The year

   @return performance_id:  The ID of the performance
           performer_name:  The name of the performer
           start_timestamp: The timestamp for when the performance
                            takes place
           profit:          The profit earned by the livehouse from this
                            performance. Zero if the performance failed to
                            meet its quota.
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_performances_in_month //
CREATE PROCEDURE get_performances_in_month (
    IN month_name VARCHAR(9),
    IN year_name INT
)
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	p.performance_id,
	pf.performer_name,
	pt.start_timestamp,
	(
		CASE
			WHEN pr.ticket_price * pr.tickets_sold > p.base_quota THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * pr.cut_percent
			ELSE 0
		END
	) AS profit
FROM
	performance p
	JOIN performance_timeslot pt ON p.performance_timeslot_id = pt.performance_timeslot_id
	JOIN performer pf ON p.performer_id = pf.performer_id
	JOIN performance_revenue pr ON p.performance_id = pr.performance_id
WHERE
	MONTHNAME (pt.start_timestamp) = month_name
	AND YEAR (pt.start_timestamp) = year_name
	AND p.performance_status = 'COMPLETE'
ORDER BY
	pt.start_timestamp DESC;
END //

/* =========================================================================
   Generates a list of all recorded 'PENDING' performances.

   @return performance_id:      The ID of the performance
           performer_name:      The name of the performer
           start_timestamp:     The timestamp for when the performance takes
                                place
           performance_status:  The status of the performance ('PENDING',
                                'COMPLETE', or 'CANCELLED')
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_pending_performances //
CREATE PROCEDURE get_pending_performances ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	p.performance_id,
	pr.performer_name,
	pt.start_timestamp,
	p.performance_status
FROM
	performance p
	JOIN performance_timeslot pt ON p.performance_timeslot_id = pt.performance_timeslot_id
	JOIN performer pr ON p.performer_id = pr.performer_id
WHERE
	p.performance_status = 'PENDING'
ORDER BY
	pt.start_timestamp DESC;
END //

/* =========================================================================
   Generates a list of all recorded performances.

   @return performance_id:  The ID of the performance
           performer_name:  The name of the performer
           start_timestamp: The timestamp for when the performance
                            takes place
           performance:     The status of the performance ('PENDING',
                            'COMPLETE', or 'CANCELLED')
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_performances //
CREATE PROCEDURE get_performances ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	p.performance_id,
	pr.performer_name,
	pt.start_timestamp,
	p.performance_status
FROM
	performance p
	JOIN performance_timeslot pt ON p.performance_timeslot_id = pt.performance_timeslot_id
	JOIN performer pr ON p.performer_id = pr.performer_id
ORDER BY
	pt.start_timestamp DESC;
END //

/* =========================================================================
   Generates a list of all equipment types (e.g. 'Electric Guitar').

   @return equipment_type_id:   The ID of the equipment type
           equipment_type_name: The name of the equipment type
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_equipment_types //
CREATE PROCEDURE get_equipment_types ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	equipment_type_id,
    equipment_type_name
FROM
	equipment_type;
END //

/* =========================================================================
   Generates a list of all timeslots.

   @return equipment_type_id:   The ID of the equipment type
           equipment_type_name: The name of the equipment type
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_timeslots //
CREATE PROCEDURE get_timeslots ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	pt.performance_timeslot_id,
    pt.start_timestamp,
    pt.end_timestamp
FROM
	performance_timeslot pt
WHERE
	(
		SELECT
			COUNT(*)
		FROM
			performance p
		WHERE
			p.performance_timeslot_id = pt.performance_timeslot_id
			AND p.performance_status <> 'CANCELLED'
	) = 0;
END //

/* =========================================================================
   Generates a list of all performers.

   @return performer_id:    The ID of the performer
           performer_name:  The name of the performer
           contact_person:  The full name of the performer's contact person
                            (e.g. 'Kazusa Kyouyama')
           contact_no:      The contact number of the performer
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_performers //
CREATE PROCEDURE get_performers ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	p.performer_id,
	p.performer_name,
	CONCAT (p.contact_first_name, ' ', p.contact_last_name) AS contact_person,
	p.contact_no
FROM
	performer p;
END //

/* =========================================================================
   Generates a list of all staff position types.

   @return position_id:     The ID for the staff position
           position_name:   The name of the position name (e.g. 'Manager')
           salary:          The base salary for the position
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_positions //
CREATE PROCEDURE get_positions ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	position_id,
    position_name,
    salary
FROM
	position_type;
END //

/* =========================================================================
   Generates a list of all recorded auditions.

   @return audition_id:     The ID of the audition
           performer_name:  The name of the performer
           submission_link: The link to the audition video
           target_datetime: The timeslot the audition is aiming for
           contact_person:  The full name of the performer's contact person
                            (e.g. 'Kazusa Kyouyama')
           contact_no:      The contact number of hte performer
           audition_status: The status of the audition (e.g. 'PENDING')
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_auditions //
CREATE PROCEDURE get_auditions ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	a.audition_id,
	p.performer_name,
	a.submission_link,
	pt.start_timestamp AS target_datetime,
	CONCAT (p.contact_first_name, ' ', p.contact_last_name) AS contact_person,
	p.contact_no,
	a.audition_status
FROM
	audition a
	LEFT JOIN performer p ON p.performer_id = a.performer_id
	LEFT JOIN performance_timeslot pt ON a.target_timeslot_id = pt.performance_timeslot_id;
END //

/* =========================================================================
   Generates a list of all currently active staff.

   @return staff_id:        The ID of the staff
           full_name:       The full name of the staff (e.g. 'Asari Neo')
           contact_no:      The contact number of the staff
           position_name:   The name of the staff's current position
           salary:          The staff's current salary
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_active_staff //
CREATE PROCEDURE get_active_staff ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	sp.staff_id,
	CONCAT (first_name, ' ', last_name) AS full_name,
	s.contact_no,
	pt.position_name,
	pt.salary
FROM
	staff s
	JOIN staff_position sp ON s.staff_id = sp.staff_id
	JOIN position_type pt ON sp.position_id = pt.position_id
WHERE
	sp.start_date = (
		SELECT
			MAX(start_date)
		FROM
			staff_position sp2
		WHERE
			sp.staff_id = sp2.staff_id
	)
	AND sp.end_date IS NULL;
END //

/* =========================================================================
   Generates a list of all staff, regardless of whether they are active.

   @return staff_id:        The ID of the staff
           full_name:       The full name of the staff (e.g. 'Asari Neo')
           contact_no:      The contact number of the staff
           position_name:   The name of the staff's current position ('N/A'
                            if they are not currently active)
           salary:          The staff's current salary ('N/A' if they are
                            not currently active)
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_staff //
CREATE PROCEDURE get_staff ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	s.staff_id,
	CONCAT (first_name, ' ', last_name) AS full_name,
	s.contact_no,
	IFNULL (pt.position_name, 'N/A'),
	IFNULL (pt.salary, 'N/A')
FROM
	staff s
	LEFT JOIN staff_position sp ON s.staff_id = sp.staff_id
	AND sp.start_date = (
		SELECT
			MAX(start_date)
		FROM
			staff_position sp2
		WHERE
			sp.staff_id = sp2.staff_id
	)
	AND sp.end_date IS NULL
	LEFT JOIN position_type pt ON sp.position_id = pt.position_id;
END //

/* =========================================================================
   Generates a list of all 'UNDAMAGED' equipment.

   @return equipment_id:        The ID of the equipmnet
           equipment_name:      The name of the equipment (e.g. 'Fender
                                Vintera II Tele')
           equipment_type_name: The type of the equipment (e.g. 'Electric
                                Guitar')
           rental_fee:          The rental fee for the equipment
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_undamaged_equipment //
CREATE PROCEDURE get_undamaged_equipment ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	e.equipment_id,
	e.equipment_name,
	et.equipment_type_name,
	e.rental_fee
FROM
	equipment e
	JOIN equipment_type et ON e.equipment_type_id = et.equipment_type_id
WHERE
	e.equipment_status = 'UNDAMAGED';
END //

/* =========================================================================
   Generates a list of all equipment, regardless of status.

   @return equipment_id:        The ID of the equipmnet
           equipment_name:      The name of the equipment (e.g. 'Fender
                                Vintera II Tele')
           equipment_type_name: The type of the equipment (e.g. 'Electric
                                Guitar')
           equipment_status:    The status of the equipment (e.g.
                                'UNDAMAGED')
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_equipment //
CREATE PROCEDURE get_equipment ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	e.equipment_id,
	e.equipment_name,
	et.equipment_type_name,
	e.equipment_status
FROM
	equipment e
	JOIN equipment_type et ON e.equipment_type_id = et.equipment_type_id;
END //

/* =========================================================================
   Generates a list of all equipment rentals.

   @return rental_id:           The ID of the equipmnet
           equipment_name:      The name of the equipment (e.g. 'Fender
                                Vintera II Tele')
           performer_name:      The type of the equipment (e.g. 'Electric
                                Guitar')
           start_date:          The start date of the rental (The equipment
                                is no longer available by this date)
           end_date:            The end date of the rental (The equipment is
                                available again by this date)
           equipment_status:    The status of the equipment (e.g.
                                'UNDAMAGED')
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_equipment_rentals //
CREATE PROCEDURE get_equipment_rentals ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	er.rental_id,
	e.equipment_name,
	p.performer_name,
	er.start_date,
	er.end_date,
	er.equipment_status
FROM
	equipment_rental er
	JOIN equipment e ON er.equipment_id = e.equipment_id
	JOIN performer p ON er.performer_id = p.performer_id
WHERE
	er.equipment_status = 'PENDING';
END //

/* =========================================================================
   Generates a list of all 'NOT_PAID' equipment rentals.

   @return rental_id:           The ID of the equipmnet
           equipment_name:      The name of the equipment (e.g. 'Fender
                                Vintera II Tele')
           performer_name:      The type of the equipment (e.g. 'Electric
                                Guitar')
           start_date:          The start date of the rental (The equipment
                                is no longer available by this date)
           end_date:            The end date of the rental (The equipment is
                                available again by this date)
           payment_status:      The status of the rental payment (e.g.
                                'NOT_PAID')
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_unpaid_rentals //
CREATE PROCEDURE get_unpaid_rentals ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	er.rental_id,
	e.equipment_name,
	p.performer_name,
	er.start_date,
	er.end_date,
	er.payment_status
FROM
	equipment_rental er
	JOIN equipment e ON er.equipment_id = e.equipment_id
	JOIN performer p ON er.performer_id = p.performer_id
WHERE
	er.payment_status = 'NOT_PAID'
	AND er.equipment_status <> 'PENDING';
END //

/* =========================================================================
   Generates a list of all active staff currently assigned to a performance.

   @return staff_id:        The ID of the staff
           full_name:       The full name of the staff (e.g. 'Asari Neo')
           contact_no:      The contact number of the staff
           position_name:   The name of the staff's current position
           salary:          The staff's current salary
   ========================================================================= */
DROP PROCEDURE IF EXISTS view_assigned_staff //
CREATE PROCEDURE view_assigned_staff (
    IN performance_id INT
)
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	s.staff_id,
	CONCAT (first_name, ' ', last_name) AS full_name,
	s.contact_no,
	IFNULL (pt.position_name, 'N/A'),
	IFNULL (pt.salary, 'N/A')
FROM
	staff s
	JOIN staff_assignment sa ON sa.staff_id = s.staff_id
	LEFT JOIN staff_position sp ON s.staff_id = sp.staff_id
	AND sp.start_date = (
		SELECT
			MAX(start_date)
		FROM
			staff_position sp2
		WHERE
			sp.staff_id = sp2.staff_id
	)
	AND sp.end_date IS NULL
	LEFT JOIN position_type pt ON sp.position_id = pt.position_id
WHERE
	sa.performance_id = performance_id;
END //

/* =========================================================================
   Generates a list of all performances assigned to a staff.

   @return performance_id:      The ID of the performance
           performer_name:      The name of the performer
           start_timestamp:     The timestamp for when the performance takes
                                place
           performance_status:  The status of the performance ('PENDING',
                                'COMPLETE', or 'CANCELLED')
   ========================================================================= */
DROP PROCEDURE IF EXISTS view_assigned_performances //
CREATE PROCEDURE view_assigned_performances (
    IN staff_id INT
)
-- ----------------------------------------------------------------------------
BEGIN
SELECT
	p.performance_id,
	pr.performer_name,
	pt.start_timestamp,
	p.performance_status
FROM
	performance p
	JOIN performance_timeslot pt ON p.performance_timeslot_id = pt.performance_timeslot_id
	JOIN performer pr ON p.performer_id = pr.performer_id
	JOIN staff_assignment sa ON sa.performance_id = p.performance_id
WHERE
	sa.staff_id = staff_id
ORDER BY
	pt.start_timestamp DESC;
END //

DELIMITER ;