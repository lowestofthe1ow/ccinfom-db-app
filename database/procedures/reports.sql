DELIMITER //

/* =========================================================================
   REPORT A: Monthly performer sales
   -------------------------------------------------------------------------
   Generates a list of the revenue, profits, and performer debts for a
   performer on a given month.

   @params performer_id:            The ID of the performer
           month_name:              The name of the month (e.g. 'January')
           year_name:               The year
   @return performer_name:          The name of the performer
           total_sales:             The total revenue generated by the
                                    performer for the month
           performer_profit_month:  The profit earned by the performer for
                                    the month (Zero if they did not exceed
                                    the performance quotas)
           performer_debt_month:    The total debt owed by the performer for
                                    the month (Zero if they met all quotas)
           livehouse_profit_month:  The profit earned by the livehouse for
                                    the month (Zero if they did not exceed
                                    the performance quotas)
   ========================================================================= */
DROP PROCEDURE IF EXISTS performer_report_month //
CREATE PROCEDURE performer_report_month (
    IN performer_id INT,
    IN month_name VARCHAR(255),
    IN year_name INT
)
-- ----------------------------------------------------------------------------
BEGIN
SELECT
    pf.performer_name,
    DATE_FORMAT (pts.start_timestamp, '%Y-%m') AS performance_month,
    SUM(pr.ticket_price * pr.tickets_sold) AS total_sales,
    SUM(
        CASE
            WHEN pr.ticket_price * pr.tickets_sold > p.base_quota THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * (1 - pr.cut_percent)
            ELSE 0
        END
    ) AS performer_profit_month,
    SUM(
        CASE
            WHEN pr.ticket_price * pr.tickets_sold < p.base_quota THEN p.base_quota - pr.ticket_price * pr.tickets_sold
            ELSE 0
        END
    ) AS performer_debt_month,
    SUM(
        CASE
            WHEN pr.ticket_price * pr.tickets_sold > p.base_quota THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * pr.cut_percent
            ELSE 0
        END
    ) AS livehouse_profit_month
FROM
    performance_revenue pr
    JOIN performance p ON pr.performance_id = p.performance_id
    JOIN performer pf ON p.performer_id = pf.performer_id
    JOIN performance_timeslot pts ON pts.performance_timeslot_id = p.performance_timeslot_id
WHERE
    pf.performer_id = performer_id
    AND MONTHNAME (pts.start_timestamp) = month_name
    AND YEAR (pts.end_timestamp) = year_name
    AND p.performance_status = 'COMPLETE'
GROUP BY
    pf.performer_name,
    performance_month;
END //

/* =========================================================================
   REPORT B: Monthly livehouse sales
   -------------------------------------------------------------------------
   Generates a list of the profits earned by the livehouse from ALL
   performances in a month.

   @params month_name:              The name of the month (e.g. 'January')
           year_name:               The year
   @return livehouse_profit_month:  The profit earned by the livehouse for
                                    the month (Zero if they did not exceed
                                    the performance quotas)
   ========================================================================= */
DROP PROCEDURE IF EXISTS cut_report_month //
CREATE PROCEDURE cut_report_month (
    IN month_name VARCHAR(255),
    IN YEAR INT
)
-- ----------------------------------------------------------------------------
BEGIN
SELECT
    SUM(
        CASE
            WHEN pr.ticket_price * pr.tickets_sold > p.base_quota THEN (pr.ticket_price * pr.tickets_sold - p.base_quota) * pr.cut_percent
            ELSE 0
        END
    ) AS livehouse_profit_month
FROM
    performance_revenue pr
    JOIN performance p ON pr.performance_id = p.performance_id
    JOIN performance_timeslot pts ON pts.performance_timeslot_id = p.performance_timeslot_id
WHERE
    MONTHNAME (pts.start_timestamp) = month_name
    AND YEAR (pts.end_timestamp) = YEAR;
END //

/* =========================================================================
   REPORT C: Monthly equipment rental sales
   -------------------------------------------------------------------------
   Generates a list of the profits earned by the livehouse from ALL
   equipment rentals in a month.

   @params month_name:          The name of the month (e.g. 'January')
           year_name:           The year
   @return equipment_name:      The name of the equipment
           rentals_month:       The number of times the equipment was rented
           rental_costs_month:  The profit earned by the livehouse for the
                                month (Zero if they did not exceed the
                                performance quotas)
   ========================================================================= */
DROP PROCEDURE IF EXISTS equipment_rental_report //
CREATE PROCEDURE equipment_rental_report (
    IN month_name VARCHAR(255),
    IN YEAR INT
)
-- ----------------------------------------------------------------------------
BEGIN
SELECT
    e.equipment_name,
    COUNT(er.rental_id) AS rentals_month,
    SUM(e.rental_fee) AS rental_costs_month
FROM
    equipment_rental er
    JOIN equipment e ON er.equipment_id = e.equipment_id
WHERE
    MONTHNAME (er.start_date) = month_name
    AND YEAR (er.start_date) = YEAR
    -- AND er.payment_status = 'PAID'
GROUP BY
    e.equipment_name
ORDER BY
    rental_costs_month DESC;
END //

/* =========================================================================
   REPORT D: Weekly livehouse schedule
   -------------------------------------------------------------------------
   Generates a scheduled list of all the performances in the current week.
   Uses the current date (by calling NOW()).

   @return performer_name:  The name of the performer
           day_name:        The name of the day of the week (e.g. 'Monday')
           start_timestamp: The start time of the performance
           end_timestamp:   The end time of the performance
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_schedule //
CREATE PROCEDURE get_schedule ()
-- ----------------------------------------------------------------------------
BEGIN
SELECT
    pf.performer_name,
    DAYNAME (pts.start_timestamp) AS day_name,
    pts.start_timestamp,
    pts.end_timestamp
FROM
    performance p
    JOIN performance_timeslot pts ON p.performance_timeslot_id = pts.performance_timeslot_id
    JOIN performer pf ON p.performer_id = pf.performer_id
WHERE
    p.performance_status = 'PENDING'
    AND YEARWEEK (pts.start_timestamp) = YEARWEEK (CURDATE())
ORDER BY
    pts.start_timestamp;
END //

/* =========================================================================
   REPORT E: Monthly staff salary report
   -------------------------------------------------------------------------
   Generates a summary of the salaries received by each staff in a given
   month. Salary is calculated as the total payments they receive for each
   performance, which is equivalent to the base salary for the position they
   held at the time of the performance.

   @params month_name:  The name of the month (e.g. 'January')
           year_name:   The year
   @return staff_id:    The ID of the staff
           staff_name:  The full name of the staff (e.g. 'Asari Neo')
           contact_no:  The contact number of the staff
           salary:      The salary received by the staff
   ========================================================================= */
DROP PROCEDURE IF EXISTS get_staff_salary //
CREATE PROCEDURE get_staff_salary (
    IN month_name VARCHAR(255),
    IN year INT
)
-- ----------------------------------------------------------------------------
BEGIN
SELECT
    sp.staff_id,
    CONCAT(s.first_name, ' ', s.last_name) AS staff_name,
    s.contact_no,
    SUM(pt.salary)
FROM staff s
    JOIN staff_position sp ON s.staff_id = sp.staff_id
	JOIN staff_assignment sa ON s.staff_id = sa.staff_id
	JOIN performance p ON sa.performance_id = p.performance_id
	JOIN performance_timeslot ps ON p.performance_timeslot_id = ps.performance_timeslot_id
	JOIN position_type pt ON sp.position_id = pt.position_id
WHERE ((sp.end_date IS NULL AND DATE(ps.start_timestamp) >= sp.start_date)
	OR (DATE(ps.start_timestamp) BETWEEN sp.start_date AND sp.end_date))
	AND YEAR(ps.start_timestamp) = 2024
	AND MONTHNAME(ps.start_timestamp) = 'November'
GROUP BY sp.staff_id
ORDER BY staff_id;
END //

DELIMITER ;