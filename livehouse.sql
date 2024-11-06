-- CCINFOM Database Application Project
-- Livehouse Management System

-- S16-03
-- MARQUESES, Lorenz Bernard B.
-- CESAR, Jusper Angelo M.
-- SILVA, Paulo Grane Gabriel C.

DROP DATABASE IF EXISTS livehouse;
CREATE DATABASE livehouse;
USE livehouse;

-- Standalone tables

CREATE TABLE performer (
	id INT NOT NULL,
    performer_name VARCHAR(255) NOT NULL,
    contact_first_name VARCHAR(255) NOT NULL,
    contact_last_name VARCHAR(255), -- TODO: Nullable?
    contact_no DECIMAL(11, 0) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE equipment (
	id INT NOT NULL,
    equipment_name VARCHAR(255) NOT NULL,
    equipment_type VARCHAR(255) NOT NULL,
    rental_fee DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE genre (
	id INT NOT NULL,
    genre_name VARCHAR(255) NOT NULL,
    genre_description VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE staff (
	id INT NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255), -- TODO: Nullable?
    contact_information VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE performance_slot (
	id INT NOT NULL,
    slot_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    PRIMARY KEY (id)
);

-- Junction tables

CREATE TABLE audition (
	id INT NOT NULL,
    performer_id INT NOT NULL,
    target_slot_id INT NOT NULL,
    submission_link VARCHAR(255) NOT NULL,
    audition_status VARCHAR(16) NOT NULL,
		-- PASSED_RESOLVED
		-- PASSED_CANCELLED
		-- PASSED_PENDING
		-- PENDING
		-- REJECTED
    PRIMARY KEY (id),
    FOREIGN KEY (performer_id) REFERENCES performer(id),
    FOREIGN KEY (target_slot_id) REFERENCES performance_slot(id)
);

CREATE TABLE performance (
	id INT NOT NULL,
    performer_id INT NOT NULL,
    genre_id INT NOT NULL,
    performance_slot_id INT NOT NULL,
    base_quota DECIMAL(10, 2), -- TODO: Nullable?
    PRIMARY KEY (id),
    FOREIGN KEY (performer_id) REFERENCES performer(id),
    FOREIGN KEY (genre_id) REFERENCES genre(id),
    FOREIGN KEY (performance_slot_id) REFERENCES performance_slot(id)
);

CREATE TABLE performance_revenue (
	performance_id INT NOT NULL,
    ticket_price DECIMAL(10, 2) NOT NULL,
    tickets_sold INT NOT NULL,
    PRIMARY KEY (performance_id),
    FOREIGN KEY (performance_id) REFERENCES performance(id)
);

CREATE TABLE equipment_rental (
	performer_id INT NOT NULL,
    equipment_id INT NOT NULL,
    start_date DATE NOT NULL, -- Equipment is unavailable by this date
    end_date DATE NOT NULL, -- Equipment is available by this date
    rental_status VARCHAR(9) NOT NULL,
		-- UNDAMAGED
		-- MIN_DMG
		-- MAJ_DMG
		-- MISSING
		-- PENDING
		-- CANCELLED
	use_type VARCHAR(11) NOT NULL,
		-- AUDITION
        -- PERFORMANCE
	PRIMARY KEY (performer_id, equipment_id, start_date, end_date),
    FOREIGN KEY (performer_id) REFERENCES performer(id),
    FOREIGN KEY (equipment_id) REFERENCES equipment(id)
);

CREATE TABLE staff_assignment (
	staff_id INT NOT NULL,
    performance_id INT NOT NULL,
    PRIMARY KEY (staff_id, performance_id),
    FOREIGN KEY (staff_id) REFERENCES staff(id),
    FOREIGN KEY (performance_id) REFERENCES performance(id)
);

CREATE TABLE staff_position (
	staff_id INT NOT NULL,
    staff_position_name VARCHAR(255) NOT NULL,
    staff_salary DECIMAL(10, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE, -- TODO: NULL if currently employed?
    PRIMARY KEY (staff_id, start_date, end_date),
    FOREIGN KEY (staff_id) REFERENCES staff(id)
);