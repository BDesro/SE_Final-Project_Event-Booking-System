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
DROP VIEW IF EXISTS user_event;

CREATE VIEW user_event AS
(
	SELECT event_name, username
    FROM users u JOIN bookings b ON u.user_id = b.user_id
				 JOIN event_list el ON b.event_id = el.event_id
) ;

INSERT INTO venues (venue_name, address) VALUES
('Omni Dome', '123 Main St, Metro City'),
('Dimmsdale Dimmadome', '456 Victory Blvd, Dimmsdale'),
('Uncle Jerry’s Basement (Legally a Venue)', '13 Suspicion St, Probably Legal, USA'),
('Spirit Halloween (Still Here in July)', '125 Haunt Plaza, Eternal Lease, USA');

INSERT INTO event_list (venue_id, event_name, event_description, is_active, event_date, start_time, end_time) VALUES
(1, 'Tax Evasion for Beginners', 'A 3-hour workshop taught by someone definitely not a CPA. Bring cash.', 1, '2025-06-22', '18:00:00', '21:00:00'),
(2, 'Live Action Emotional Baggage Claim', 'Reenact losing your luggage, except it’s your childhood trauma.', 1, '2025-07-02', '19:00:00', '22:00:00'),
(3, 'Speed Dating But It’s a Job Interview', 'Answer “Where do you see this relationship in 5 years?” in under 30 seconds.', 1, '2025-07-10', '20:00:00', '22:00:00'),
(4, 'PowerPoint Night: Defend the Indefensible', 'Can you convince a crowd that pineapple belongs on tacos? Now’s your chance.', 1, '2025-07-15', '19:30:00', '21:30:00'),
(1, 'Cat Yoga Featuring Zero Trained Cats', 'It’s just a yoga class but 10 stray cats might show up. We don’t know.', 1, '2025-07-20', '08:00:00', '09:30:00'),
(4, 'FNaF Roleplay: Survive the Shift','Dress up, roleplay, and try to survive your night shift with haunted animatronics. Flashlights encouraged, trauma unavoidable.', 1, '2025-08-10','21:00:00' ,'23:30:00'),
(2, 'AI vs Human: Who Can Write Worse Poetry?', 'You vs a neural network in a haiku deathmatch. No winners, only cringe.', 1, '2025-08-01', '18:00:00', '20:00:00');
