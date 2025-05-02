-- -----------------------------------------------------
-- Database etickets
-- -----------------------------------------------------
DROP DATABASE IF EXISTS omni_vent;

CREATE DATABASE omni_vent;
USE omni_vent;


-- -----------------------------------------------------
-- Table users
-- -----------------------------------------------------
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
  user_id         INT                     PRIMARY KEY   AUTO_INCREMENT,
  username        VARCHAR(45)             NOT NULL      UNIQUE,
  nickname		  VARCHAR(45),
  password_hash   VARCHAR(255)            NOT NULL,
  email_address   VARCHAR(50)             NOT NULL      UNIQUE,
  user_role       ENUM('user', 'admin')   NOT NULL      DEFAULT 'user',
  is_active	      TINYINT                 NOT NULL      DEFAULT 0,
  avatar		  ENUM('FourArms','CannonBolt','DiamondHead','GreyMatter','HeatBlast','RipJaw','StinkFly','UpChuck','Upgrade','WildMutt','XLR8','Default')  NOT NULL DEFAULT 'Default'
) AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Trigger To Initialize Nickname
-- -----------------------------------------------------
DROP TRIGGER IF EXISTS set_nickname_before_insert;
DELIMITER //

CREATE TRIGGER set_nickname_before_insert
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
  IF NEW.nickname IS NULL THEN
    SET NEW.nickname = NEW.username;
  END IF;
END//

DELIMITER ;

-- -----------------------------------------------------
-- Trigger To Prevent Guests from Changing Their Values
-- -----------------------------------------------------
DROP TRIGGER IF EXISTS stop_guest_user_update;

DELIMITER //
CREATE TRIGGER stop_guest_user_update
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
IF OLD.username = 'Guest User' THEN
	IF NOT(OLD.nickname = NEW.nickname AND OLD.avatar = NEW.avatar) THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Guest Users Cannot Update Their Info';
	END IF;
END IF;
END //
DELIMITER ;
-- -----------------------------------------------------
-- Table seats
-- -----------------------------------------------------
DROP TABLE IF EXISTS seats;

CREATE TABLE seats
(
  seat_id        INT                                  PRIMARY KEY   AUTO_INCREMENT,
  seat_section   CHAR(1)                              NULL,
  seat_row       CHAR(1)                              NULL,
  seat_number    INT                                  NOT NULL      UNIQUE,
  seat_type      ENUM('standard', 'premium', 'vip')   NOT NULL      DEFAULT 'standard',
  is_active      TINYINT                              NOT NULL      DEFAULT 1,
  price          DECIMAL(5,2)                         NOT NULL      DEFAULT 20.00
) AUTO_INCREMENT = 1;

-- =====================================================
-- Table venues
-- =====================================================
DROP TABLE IF EXISTS venues;

CREATE TABLE venues
(
	venue_id INT PRIMARY KEY AUTO_INCREMENT,
    venue_name VARCHAR(50) NOT NULL UNIQUE,
    address VARCHAR(50) NOT NULL 	UNIQUE
) AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table event_list
-- -----------------------------------------------------
DROP TABLE IF EXISTS event_list;

CREATE TABLE event_list
(
  event_id            INT            PRIMARY KEY   AUTO_INCREMENT,
  venue_id            INT            NOT NULL,
  event_name          VARCHAR(45)    NOT NULL      UNIQUE,
  event_description   VARCHAR(500)   NOT NULL      DEFAULT 'No Description',
  is_active           TINYINT        NOT NULL      DEFAULT 0,
  event_date          DATE           NOT NULL      UNIQUE,
  start_time          TIME           NOT NULL      DEFAULT '17:00:00',
  end_time            TIME           NOT NULL      DEFAULT '20:00:00',

	CONSTRAINT venue_id_events_fk FOREIGN KEY (venue_id) REFERENCES venues (venue_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table bookings
-- -----------------------------------------------------
DROP TABLE IF EXISTS bookings;

CREATE TABLE bookings
(
  booking_id     INT        PRIMARY KEY   AUTO_INCREMENT,
  user_id        INT        NOT NULL,
  seat_id        INT        NOT NULL,
  event_id       INT        NOT NULL,
  venue_id       INT        NOT NULL,
  booking_time   DATETIME   NOT NULL      DEFAULT NOW(),

  CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT seat_id_fk FOREIGN KEY (seat_id) REFERENCES seats (seat_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT event_id_fk FOREIGN KEY (event_id) REFERENCES event_list (event_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT venue_id_fk FOREIGN KEY (venue_id) REFERENCES venues (venue_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Example/Testing Table Manipulations
-- -----------------------------------------------------
INSERT INTO users
	(username, password_hash, email_address, user_role)
VALUES
	('Guest User', '$2a$10$O39LWmBVdc9qzYt5FI62quQSPbvPfwzZdGTLWcWnh//Zqm0mldBie', 'N/A', DEFAULT);

INSERT INTO users
    (username, password_hash, email_address, user_role)
VALUES
    ('King Rhoam Bosphoramus Hyrule','$2a$10$sGToUTOEKwn7dYg79YobTO9dR3tksHqtke3M701Lu4fUH2/PGb59i', 'N/A1', 'admin');

-- -----------------------------------------------------
-- Adding View Table to Connect venue and event
-- -----------------------------------------------------
DROP VIEW IF EXISTS event_venue;

CREATE VIEW event_venue AS
(
	SELECT event_name, event_description, event_date, is_active, venue_name, address
    FROM event_list JOIN venues ON event_list.venue_id = venues.venue_id
) ;