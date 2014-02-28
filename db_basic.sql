CREATE DATABASE IF NOT EXISTS `mydb`;
USE `mydb`;

CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `user_id` VARCHAR(10) NOT NULL,
  `e_mail` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `e_mail_UNIQUE` (`e_mail` ASC),
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`user_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ticket_num` INT UNSIGNED NOT NULL,
  `main_character_id` INT UNSIGNED NOT NULL,
  `level` INT UNSIGNED NOT NULL,
  `exp` INT UNSIGNED NOT NULL,
  `user_id` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  CHECK(`ticket_num` <= 100),
  INDEX `fk_user_detail_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_detail_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `mydb`.`character` (
  `character_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(10) NOT NULL,
  `grade` INT UNSIGNED NOT NULL,
  `hp` INT UNSIGNED NOT NULL,
  `damage` INT NOT NULL,
  `img` BLOB NOT NULL,
  `expr` INT NOT NULL,
  PRIMARY KEY (`character_id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO user (user_id, e_mail) VALUES ('jehyeok', 'hook3748@gmail.com');
INSERT INTO user (user_id, e_mail) VALUES ('suchan', 'doobestan@nhnnext.org');

INSERT INTO `character` (user_id, grade, hp, damage, img, expr)
VALUES ('jehyeok', 1, 100, 10, 'img', 100);
INSERT INTO `character` (user_id, grade, hp, damage, img, expr)
VALUES ('jehyeok', 2, 200, 20, 'img', 200);
INSERT INTO `character` (user_id, grade, hp, damage, img, expr)
VALUES ('jehyeok', 3, 300, 30, 'img', 300);
INSERT INTO `character` (user_id, grade, hp, damage, img, expr)
VALUES ('jehyeok', 4, 400, 40, 'img', 400);


DROP PROCEDURE IF EXISTS register;
DELIMITER $$
CREATE PROCEDURE register(IN user_id VARCHAR(10), IN e_mail VARCHAR(45))
  BEGIN
	INSERT INTO `user` (user_id, e_mail) VALUES (user_id, e_mail);
  END $$
DELIMITER ;

#CALL register('jehyeok', 'hook3748@gmail.com');

DROP PROCEDURE IF EXISTS pick_character;
DELIMITER $$
CREATE PROCEDURE pick_character(IN user_id VARCHAR(10))
  BEGIN
	DECLARE grade INT;
	SET grade = FLOOR(1 + RAND() * 5);
	INSERT INTO `character` (user_id, grade, hp, damage, img, expr)
	VALUES (user_id, grade, 100 * grade, 10 * grade, 'img', 100*grade);
  END $$
DELIMITER ;

#CALL pick_character('jehyeok');

SELECT * FROM user;
SELECT * FROM `character`;