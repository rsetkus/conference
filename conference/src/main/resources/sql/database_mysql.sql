SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `ConferencesPortal` ;
CREATE SCHEMA IF NOT EXISTS `ConferencesPortal` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `ConferencesPortal` ;

-- -----------------------------------------------------
-- Table `ConferencesPortal`.`File`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConferencesPortal`.`File` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `ConferencesPortal`.`File` (
  `fileId` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `path` VARCHAR(45) NOT NULL ,
  `mimeType` VARCHAR(45) NULL ,
  PRIMARY KEY (`fileId`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConferencesPortal`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConferencesPortal`.`User` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `ConferencesPortal`.`User` (
  `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NOT NULL ,
  `password` VARCHAR(256) NOT NULL ,
  `fileId` INT UNSIGNED NULL COMMENT 'Avatar image file id' ,
  `name` VARCHAR(256) CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NOT NULL ,
  `surname` VARCHAR(256) CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NOT NULL ,
  `enabled` TINYINT(1) NULL DEFAULT 1 ,
  PRIMARY KEY (`userId`) ,
  CONSTRAINT `fk_Users_Files1`
    FOREIGN KEY (`fileId` )
    REFERENCES `ConferencesPortal`.`File` (`fileId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_Users_Files1_idx` ON `ConferencesPortal`.`User` (`fileId` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConferencesPortal`.`ConferenceType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConferencesPortal`.`ConferenceType` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `ConferencesPortal`.`ConferenceType` (
  `conferenceTypeId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NOT NULL ,
  PRIMARY KEY (`conferenceTypeId`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `name_UNIQUE` ON `ConferencesPortal`.`ConferenceType` (`name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConferencesPortal`.`Conference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConferencesPortal`.`Conference` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `ConferencesPortal`.`Conference` (
  `conferenceId` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `conferenceTypeId` INT UNSIGNED NOT NULL ,
  `conferenceFrom` DATE NOT NULL COMMENT 'Conference begins at' ,
  `conferenceTill` DATE NOT NULL COMMENT 'Conference ends at' ,
  `title` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NOT NULL COMMENT 'Conference title' ,
  `teaser` TINYTEXT CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NOT NULL COMMENT 'Conference teaser. It\'s shown on the list' ,
  `address` VARCHAR(256) CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NOT NULL DEFAULT '' ,
  `description` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NULL COMMENT 'Condeference detail description' ,
  `isPublished` TINYINT(1) NULL DEFAULT false ,
  PRIMARY KEY (`conferenceId`, `conferenceTypeId`) ,
  CONSTRAINT `fk_Conferences_ConferenceTypes1`
    FOREIGN KEY (`conferenceTypeId` )
    REFERENCES `ConferencesPortal`.`ConferenceType` (`conferenceTypeId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_Conferences_ConferenceTypes1_idx` ON `ConferencesPortal`.`Conference` (`conferenceTypeId` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConferencesPortal`.`Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConferencesPortal`.`Roles` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `ConferencesPortal`.`Roles` (
  `roleId` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `role` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_lithuanian_ci' NOT NULL ,
  PRIMARY KEY (`roleId`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `role_UNIQUE` ON `ConferencesPortal`.`Roles` (`role` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConferencesPortal`.`UsersRoles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConferencesPortal`.`UsersRoles` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `ConferencesPortal`.`UsersRoles` (
  `roleId` INT UNSIGNED NOT NULL ,
  `userId` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`roleId`, `userId`) ,
  CONSTRAINT `fk_UsersRoles_Roles`
    FOREIGN KEY (`roleId` )
    REFERENCES `ConferencesPortal`.`Roles` (`roleId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UsersRoles_Users1`
    FOREIGN KEY (`userId` )
    REFERENCES `ConferencesPortal`.`User` (`userId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_UsersRoles_Users1_idx` ON `ConferencesPortal`.`UsersRoles` (`userId` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `ConferencesPortal`.`Attendee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConferencesPortal`.`Attendee` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `ConferencesPortal`.`Attendee` (
  `attendeeId` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `userId` INT UNSIGNED NOT NULL ,
  `conferenceId` INT UNSIGNED NOT NULL ,
  `isSpeaker` TINYINT(1) NULL DEFAULT 0 ,
  PRIMARY KEY (`attendeeId`, `userId`, `conferenceId`) ,
  CONSTRAINT `fkUserId`
    FOREIGN KEY (`userId` )
    REFERENCES `ConferencesPortal`.`User` (`userId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fkConferenceId`
    FOREIGN KEY (`conferenceId` )
    REFERENCES `ConferencesPortal`.`Conference` (`conferenceId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fkUserIdIndex` ON `ConferencesPortal`.`Attendee` (`userId` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `attendee` ON `ConferencesPortal`.`Attendee` (`userId` ASC, `conferenceId` ASC, `attendeeId` ASC) ;

SHOW WARNINGS;
CREATE INDEX `fkConferenceIdIndex` ON `ConferencesPortal`.`Attendee` (`conferenceId` ASC) ;

SHOW WARNINGS;
USE `ConferencesPortal` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `ConferencesPortal`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `ConferencesPortal`;
INSERT INTO `ConferencesPortal`.`User` (`userId`, `username`, `password`, `fileId`, `name`, `surname`, `enabled`) VALUES (NULL, 'robertas.setkus@gmail.com', 'd7f61fe966084df473b011d673121bdd', NULL, 'Robertas', 'Šetkus', 1);
INSERT INTO `ConferencesPortal`.`User` (`userId`, `username`, `password`, `fileId`, `name`, `surname`, `enabled`) VALUES (NULL, 'rsetkus@gmail.com', '965364f4b7094b06b243c90914d72ad8', NULL, 'Ričerdas', 'Šetkus', 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `ConferencesPortal`.`ConferenceType`
-- -----------------------------------------------------
START TRANSACTION;
USE `ConferencesPortal`;
INSERT INTO `ConferencesPortal`.`ConferenceType` (`conferenceTypeId`, `name`) VALUES (NULL, 'Agro Culture');
INSERT INTO `ConferencesPortal`.`ConferenceType` (`conferenceTypeId`, `name`) VALUES (NULL, 'Home Design and Interior');
INSERT INTO `ConferencesPortal`.`ConferenceType` (`conferenceTypeId`, `name`) VALUES (NULL, 'Industry');
INSERT INTO `ConferencesPortal`.`ConferenceType` (`conferenceTypeId`, `name`) VALUES (NULL, 'Information Technologies');

COMMIT;

-- -----------------------------------------------------
-- Data for table `ConferencesPortal`.`Conference`
-- -----------------------------------------------------
START TRANSACTION;
USE `ConferencesPortal`;
INSERT INTO `ConferencesPortal`.`Conference` (`conferenceId`, `conferenceTypeId`, `conferenceFrom`, `conferenceTill`, `title`, `teaser`, `address`, `description`, `isPublished`) VALUES (NULL, 1, '2013-12-01', '2013-12-05', 'Logout 2013', 'Non technology conference', 'Lietuvos parodų ir kongresų centras LITEXPO, Laisvės pr. 5, Vilnius', 'Most biggest advertising and manufactures promoting \"conferece\" in baltic', 1);
INSERT INTO `ConferencesPortal`.`Conference` (`conferenceId`, `conferenceTypeId`, `conferenceFrom`, `conferenceTill`, `title`, `teaser`, `address`, `description`, `isPublished`) VALUES (NULL, 3, '2013-10-31', '2013-11-05', 'Kas nedirba, tam ir duonos duot nereikia', 'Tarptautinė žemės ūkio paroda', 'Universiteto g. 8a-206, Akademija, Kaunas', '„XXXX  2013“ dalyvavo 313 įmonių, įstaigų ir organizacijų bei apie 200 smulkiųjų verslų atstovų. Dvylika įmonių į „Ką pasėsi... 2013“ atvyko iš užsienio – Lenkijos, Danijos, Latvijos, Italijos, Rusijos. Parodos dalyviai sulaukė svečių iš 16 pasaulio šalių. Per tris dienas parodoje apsilankė apie 100 tūkst. žmonių. Planuojama, kad žemės ūkio paroda „XXX 2014“ bus dar gausesnė.', NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `ConferencesPortal`.`Roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `ConferencesPortal`;
INSERT INTO `ConferencesPortal`.`Roles` (`roleId`, `role`) VALUES (NULL, 'ROLE_ADMINISTRATOR');
INSERT INTO `ConferencesPortal`.`Roles` (`roleId`, `role`) VALUES (NULL, 'ROLE_MANAGER');
INSERT INTO `ConferencesPortal`.`Roles` (`roleId`, `role`) VALUES (NULL, 'ROLE_VIEWER');
INSERT INTO `ConferencesPortal`.`Roles` (`roleId`, `role`) VALUES (NULL, 'ROLE_GUEST');

COMMIT;

-- -----------------------------------------------------
-- Data for table `ConferencesPortal`.`UsersRoles`
-- -----------------------------------------------------
START TRANSACTION;
USE `ConferencesPortal`;
INSERT INTO `ConferencesPortal`.`UsersRoles` (`roleId`, `userId`) VALUES (1, 1);
INSERT INTO `ConferencesPortal`.`UsersRoles` (`roleId`, `userId`) VALUES (2, 1);
INSERT INTO `ConferencesPortal`.`UsersRoles` (`roleId`, `userId`) VALUES (3, 1);
INSERT INTO `ConferencesPortal`.`UsersRoles` (`roleId`, `userId`) VALUES (3, 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `ConferencesPortal`.`Attendee`
-- -----------------------------------------------------
START TRANSACTION;
USE `ConferencesPortal`;
INSERT INTO `ConferencesPortal`.`Attendee` (`attendeeId`, `userId`, `conferenceId`, `isSpeaker`) VALUES (NULL, 1, 1, 1);
INSERT INTO `ConferencesPortal`.`Attendee` (`attendeeId`, `userId`, `conferenceId`, `isSpeaker`) VALUES (NULL, 2, 1, NULL);

COMMIT;
