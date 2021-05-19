CREATE TABLE `shops` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NOT NULL,
  `location` VARCHAR(255) NOT NULL,
  `work_hours` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `pick_up_points` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `city` VARCHAR(255) NULL,
  `location` VARCHAR(255) NOT NULL,
  `work_hours` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));

INSERT INTO `shops` (`city`, `location`, `work_hours`) VALUES ('moscow', 'street 1', '8-20');
INSERT INTO `shops` (`city`, `location`, `work_hours`) VALUES ('moscow', 'street 2', '8-20');
INSERT INTO `shops` (`city`, `location`, `work_hours`) VALUES ('moscow', 'street 3', '8-20');
INSERT INTO `pick_up_points` (`name`, `city`, `location`) VALUES ('one', 'moscow', 'street 1');
INSERT INTO `pick_up_points` (`name`, `city`, `location`) VALUES ('two', 'moscow', 'street 2');
