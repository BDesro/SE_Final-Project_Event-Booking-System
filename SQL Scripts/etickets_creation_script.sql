-- -----------------------------------------------------
-- Database etickets
-- -----------------------------------------------------
DROP DATABASE IF EXISTS etickets;

CREATE DATABASE etickets;
USE etickets;


-- -----------------------------------------------------
-- Table etickets.users
-- -----------------------------------------------------
DROP TABLE IF EXISTS etickets.users;

CREATE TABLE etickets.users
(
  user_id         INT                     PRIMARY KEY   AUTO_INCREMENT,
  username        VARCHAR(45)             NOT NULL      UNIQUE,
  password_hash   VARCHAR(255)            NOT NULL,
  email_address   VARCHAR(50)             NOT NULL      UNIQUE,
  user_role       ENUM('user', 'admin')   NOT NULL      DEFAULT 'user'
) AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table etickets.seats
-- -----------------------------------------------------
DROP TABLE IF EXISTS etickets.seats;

CREATE TABLE etickets.seats
(
  seat_id        INT                                  PRIMARY KEY   AUTO_INCREMENT,
  seat_section   CHAR(1)                              NULL,
  seat_row       CHAR(1)                              NULL,
  seat_number    INT                                  NOT NULL      UNIQUE,
  seat_type      ENUM('standard', 'premium', 'vip')   NOT NULL      DEFAULT 'standard',
  is_active      TINYINT                              NOT NULL      DEFAULT 1,
  price          DECIMAL(5,2)                         NOT NULL      DEFAULT 20.00
) AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table etickets.event_list
-- -----------------------------------------------------
DROP TABLE IF EXISTS etickets.event_list;

CREATE TABLE etickets.event_list
(
  event_id            INT            PRIMARY KEY   AUTO_INCREMENT,
  event_name          VARCHAR(45)    NOT NULL      UNIQUE,
  event_description   VARCHAR(500)   NOT NULL      DEFAULT 'No Description',
  is_active           TINYINT        NOT NULL      DEFAULT 0,
  event_date          DATE           NOT NULL      UNIQUE,
  start_time          TIME           NOT NULL      DEFAULT '17:00:00',
  end_time            TIME           NOT NULL      DEFAULT '20:00:00'
) AUTO_INCREMENT = 1;


-- -----------------------------------------------------
-- Table etickets.bookings
-- -----------------------------------------------------
DROP TABLE IF EXISTS etickets.bookings;

CREATE TABLE etickets.bookings
(
  booking_id     INT        PRIMARY KEY   AUTO_INCREMENT,
  user_id        INT        NOT NULL,
  seat_id        INT        NOT NULL,
  event_id       INT        NOT NULL,
  booking_time   DATETIME   NOT NULL      DEFAULT NOW(),
  
  UNIQUE (event_id, seat_id),
  INDEX user_id_idx (user_id ASC) VISIBLE,
  INDEX seat_id_idx (seat_id ASC) VISIBLE,
  INDEX event_id_idx (event_id ASC) VISIBLE,
  
  CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES etickets.users (user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT seat_id FOREIGN KEY (seat_id) REFERENCES etickets.seats (seat_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT event_id FOREIGN KEY (event_id) REFERENCES etickets.event_list (event_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Example/Testing Table Manipulations
-- -----------------------------------------------------
INSERT INTO users
	(username, password_hash, email_address, user_role)
VALUES
<<<<<<< Updated upstream
	('Guest User', 'N/A', 'N/A', DEFAULT);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
	('Guest User', 'N/A', 'N/A', DEFAULT);
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

	INSERT INTO users
    (username, password_hash, email_address, user_role)
    VALUES
    ('King Rhoam Bosphoramus ','N/A', 'N/A1', 'admin')
>>>>>>> Stashed changes
