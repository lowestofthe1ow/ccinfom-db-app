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
    pr.performer_name,
    DATE_FORMAT (pct.start_timestamp, '%Y-%m') AS performance_month,
    SUM(pcr.ticket_price * pcr.tickets_sold) AS total_sales,
    SUM(
        CASE
            WHEN pcr.ticket_price * pcr.tickets_sold > pc.base_quota
                THEN (pcr.ticket_price * pcr.tickets_sold - pc.base_quota) * (1 - pcr.cut_percent)
            ELSE 0
        END
    ) AS performer_profit_month,
    SUM(
        CASE
            WHEN pcr.ticket_price * pcr.tickets_sold < pc.base_quota
                THEN pc.base_quota - pcr.ticket_price * pcr.tickets_sold
            ELSE 0
        END
    ) AS performer_debt_month,
    SUM(
        CASE
            WHEN pcr.ticket_price * pcr.tickets_sold > pc.base_quota
                THEN (pcr.ticket_price * pcr.tickets_sold - pc.base_quota) * pcr.cut_percent
            ELSE 0
        END
    ) AS livehouse_profit_month
FROM
    performance_revenue pcr
    JOIN performance pc ON pcr.performance_id = pc.performance_id
    JOIN performer pr ON pc.performer_id = pr.performer_id
    JOIN performance_timeslot pct ON pct.performance_timeslot_id = pc.performance_timeslot_id
WHERE
    pr.performer_id = performer_id
    AND MONTHNAME (pct.start_timestamp) = month_name
    AND YEAR (pct.end_timestamp) = year_name
    AND pc.performance_status = 'COMPLETE'
GROUP BY
    pr.performer_name,
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
            WHEN pcr.ticket_price * pcr.tickets_sold > pc.base_quota THEN (pcr.ticket_price * pcr.tickets_sold - pc.base_quota) * pcr.cut_percent
            ELSE 0
        END
    ) AS livehouse_profit_month
FROM
    performance_revenue pcr
    JOIN performance pc ON pcr.performance_id = pc.performance_id
    JOIN performance_timeslot pct ON pct.performance_timeslot_id = pc.performance_timeslot_id
WHERE
    MONTHNAME (pct.start_timestamp) = month_name
    AND YEAR (pct.end_timestamp) = YEAR;
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
    COUNT(er.rental_id) AS rentals_count,
    -- SUM(er.end_date - er.start_date) AS rental_days,
    SUM(e.rental_fee * (er.end_date - er.start_date)) AS rental_costs_month
    -- e.rental_fee AS rental_fee
FROM
    equipment_rental er
    JOIN equipment e ON er.equipment_id = e.equipment_id
WHERE
    MONTHNAME (er.start_date) = month_name
    AND YEAR (er.start_date) = YEAR
    AND er.payment_status = 'PAID'
GROUP BY
    e.equipment_name, rental_fee
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
    pr.performer_name,
    DAYNAME (pct.start_timestamp) AS day_name,
    pct.start_timestamp,
    pct.end_timestamp
FROM
    performance pc
    JOIN performance_timeslot pct ON pc.performance_timeslot_id = pct.performance_timeslot_id
    JOIN performer pr ON pc.performer_id = pr.performer_id
WHERE
    pc.performance_status = 'PENDING'
    AND YEARWEEK (pct.start_timestamp) = YEARWEEK (CURDATE())
ORDER BY
    pct.start_timestamp;
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
    spo.staff_id,
    CONCAT(s.first_name, ' ', s.last_name) AS staff_name,
    s.contact_no,
    SUM(po.salary)
FROM staff s
    JOIN staff_position spo ON s.staff_id = spo.staff_id
	JOIN staff_assignment sa ON s.staff_id = sa.staff_id
	JOIN performance pc ON sa.performance_id = pc.performance_id
	JOIN performance_timeslot pct ON pc.performance_timeslot_id = pct.performance_timeslot_id
	JOIN position_type po ON spo.position_id = po.position_id
WHERE ((spo.end_date IS NULL AND DATE(pct.start_timestamp) >= spo.start_date)
	OR (DATE(pct.start_timestamp) BETWEEN spo.start_date AND spo.end_date))
    AND sa.assignment_status = 'ASSIGNED'
    AND pc.performance_status = 'COMPLETE'
	AND YEAR(pct.start_timestamp) = year
	AND MONTHNAME(pct.start_timestamp) = month_name
GROUP BY spo.staff_id
ORDER BY staff_id;
END //

DELIMITER ;