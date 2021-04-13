CREATE TABLE `delivery_types` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

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
