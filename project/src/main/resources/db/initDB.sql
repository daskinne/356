SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
DROP database if exists `ece356`;
CREATE database IF NOT EXISTS `ece356` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `ece356` ;

-- -----------------------------------------------------
-- Table `ece356`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`user` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `password` VARCHAR(45) NOT NULL,
    `Name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`user_id`)
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`doctor` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `pay_rate` FLOAT NOT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (`user_id`)
        REFERENCES `ece356`.`user` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`patient` (
    `user_id` INT NOT NULL,
    `version_number` INT NOT NULL AUTO_INCREMENT,
    `phone_number` VARCHAR(15) NOT NULL,
    `health_card` CHAR(12) NOT NULL,
    `sin` CHAR(9) NOT NULL,
    `address` VARCHAR(45) NOT NULL,
    `current_health` SMALLINT NOT NULL,
    `doctor_account` INT NOT NULL,
    PRIMARY KEY (`version_number` , `user_id`),
    INDEX `doctor_account_idx` (`doctor_account` ASC),
    FOREIGN KEY (`user_id`)
        REFERENCES `ece356`.`user` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`doctor_account`)
        REFERENCES `ece356`.`doctor` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`pati_doct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`pati_doct` (
    `doctor_account` INT NOT NULL AUTO_INCREMENT,
    `patient_account` INT NOT NULL,
    `patient_version_number` INT NOT NULL,
    PRIMARY KEY (`doctor_account` , `patient_account` , `patient_version_number`),
    INDEX `version_number_idx` (`patient_version_number` ASC),
    INDEX `account_idx` (`patient_account` ASC),
    FOREIGN KEY (`patient_account`)
        REFERENCES `ece356`.`patient` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`doctor_account`)
        REFERENCES `ece356`.`doctor` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`patient_version_number`)
        REFERENCES `ece356`.`patient` (`version_number`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`user_doct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`user_doct` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `doctor_account` INT NOT NULL,
    PRIMARY KEY (`user_id` , `doctor_account`),
    FOREIGN KEY (`doctor_account`)
        REFERENCES `ece356`.`doctor` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`user_id`)
        REFERENCES `ece356`.`user` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`legal_prescription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`legal_prescription` (
    `medication` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`medication`)
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`doct_lega`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`doct_lega` (
    `doctor_user` INT NOT NULL AUTO_INCREMENT,
    `legal_prescription_medication` INT NOT NULL,
    PRIMARY KEY (`doctor_user` , `legal_prescription_medication`),
    INDEX `medication_idx` (`legal_prescription_medication` ASC),
    FOREIGN KEY (`doctor_user`)
        REFERENCES `ece356`.`doctor` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`legal_prescription_medication`)
        REFERENCES `ece356`.`legal_prescription` (`medication`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`appointment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`appointment` (
    `patient_account` INT NOT NULL,
    `patient_version_number` INT NOT NULL,
    `doctor_account` INT NOT NULL,
    `version_number` INT NOT NULL AUTO_INCREMENT,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    PRIMARY KEY (`version_number` , `patient_account` , `patient_version_number` , `start_time`),
    INDEX `account_idx` (`patient_account` ASC),
    INDEX `doctor_idx` (`doctor_account` ASC),
    INDEX `version_number_idx` (`patient_version_number` ASC),
    FOREIGN KEY (`patient_account`, `patient_version_number`)
        REFERENCES `ece356`.`patient` (`user_id`, `version_number`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`doctor_account`)
        REFERENCES `ece356`.`doctor` (`user_id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`visit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`visit` (
    `appointment_patient_account` INT NOT NULL,
    `appointment_patient_version_number` INT NOT NULL,
    `appointment_version_number` INT NOT NULL,
    `appointment_start_time` DATETIME NOT NULL,
    `diagnosis` VARCHAR(45) NOT NULL,
    `diagnostic_procedure` VARCHAR(45) NOT NULL,
    `doctor_comments` VARCHAR(2000) NOT NULL,
    PRIMARY KEY (`appointment_version_number` , `appointment_patient_account` , `appointment_patient_version_number` , `appointment_start_time`),
    FOREIGN KEY (`appointment_patient_account`, `appointment_patient_version_number`, `appointment_version_number`, `appointment_start_time`)
        REFERENCES `ece356`.`appointment` (`patient_account`,`patient_version_number`,`version_number`,`start_time`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`lega_visi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`lega_visi` (
    `visit_appointment_patient_account` INT NOT NULL,
    `visit_appointment_patient_version_number` INT NOT NULL,
    `visit_appointment_version_number` INT NOT NULL,
    `visit_appointment_start_time` DATETIME NOT NULL,
    `legal_prescription_medication` INT NOT NULL,
    `end_date` DATETIME NOT NULL,
    `dosage` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`visit_appointment_patient_account` , `visit_appointment_patient_version_number` , `visit_appointment_version_number` , `visit_appointment_start_time` , `legal_prescription_medication`),
    INDEX `patient_version_number_idx` (`visit_appointment_patient_version_number` ASC),
    INDEX `version_number_idx` (`visit_appointment_version_number` ASC),
    INDEX `start_time_idx` (`visit_appointment_start_time` ASC),
    INDEX `medication_idx` (`legal_prescription_medication` ASC),
    FOREIGN KEY (`visit_appointment_patient_account`,`visit_appointment_patient_version_number`, `visit_appointment_version_number`, `visit_appointment_start_time`)
        REFERENCES `ece356`.`visit` (`appointment_patient_account`,`appointment_patient_version_number`, `appointment_version_number`,`appointment_start_time`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`legal_prescription_medication`)
        REFERENCES `ece356`.`legal_prescription` (`medication`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


-- -----------------------------------------------------
-- Table `ece356`.`treatment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ece356`.`treatment` (
    `visit_appointment_patient_account` INT NOT NULL AUTO_INCREMENT,
    `visit_appointment_patient_version_number` INT NOT NULL,
    `visit_appointment_version_number` INT NOT NULL,
    `visit_appointment_start_time` TIMESTAMP NOT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `procedure` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`visit_appointment_patient_account` , `visit_appointment_patient_version_number` , `visit_appointment_version_number` , `visit_appointment_start_time`),
    INDEX `patient_version_number_idx` (`visit_appointment_patient_version_number` ASC),
    INDEX `version_number_idx` (`visit_appointment_version_number` ASC),
    INDEX `start_time_idx` (`visit_appointment_start_time` ASC),
    FOREIGN KEY (`visit_appointment_patient_account`,`visit_appointment_patient_version_number`,`visit_appointment_version_number`,`visit_appointment_start_time`)
        REFERENCES `ece356`.`visit` (`appointment_patient_account`,`appointment_patient_version_number`,`appointment_version_number`,`appointment_start_time`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
