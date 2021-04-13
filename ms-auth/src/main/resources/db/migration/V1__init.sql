CREATE TABLE `roles` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

insert into roles (name) values ('ROLE_ADMIN');
insert into roles (name) values ('ROLE_USER');

CREATE TABLE `users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `fio` VARCHAR(256) NULL,
  `phone` VARCHAR(45) NULL,
  `email` VARCHAR(256) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE);

CREATE TABLE `users_roles` (
  `id_user` INT UNSIGNED NOT NULL,
  `id_role` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id_user`, `id_role`),
  INDEX `fk_users_roles_role_id_idx` (`id_role` ASC) VISIBLE,
  CONSTRAINT `fk_users_roles_user_id`
    FOREIGN KEY (`id_user`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_users_roles_role_id`
    FOREIGN KEY (`id_role`)
    REFERENCES `roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE `user_delivery_addresses` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_user` INT UNSIGNED NOT NULL,
  `city` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  `house` VARCHAR(45) NULL,
  `postal_code` VARCHAR(45) NULL,
  `apt` VARCHAR(45) NULL,
  `add_info` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_addresses_users_user_id`
    FOREIGN KEY (`id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT);

