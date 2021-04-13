CREATE TABLE `sessions` (
  `guid` BINARY(16) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`guid`));

CREATE TABLE `basket` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `session_guid` BINARY(16) NULL,
  `id_user` INT UNSIGNED NULL,
  `id_product` INT UNSIGNED NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_basket_session_guid_idx` (`session_guid` ASC) VISIBLE,
  CONSTRAINT `fk_basket_session_guid`
    FOREIGN KEY (`session_guid`)
    REFERENCES `sessions` (`guid`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE `order_status` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `promo` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `promo_code` VARCHAR(45) NOT NULL,
  `exp_date` DATETIME NOT NULL,
  `discount_abs` FLOAT NULL,
  `discount_percent` TINYINT NULL,
  `min_order_price` FLOAT NULL,
  `is_applied` TINYINT NOT NULL DEFAULT 0 COMMENT '0 - не применен, 1 - применен',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `promo_code_UNIQUE` (`promo_code` ASC) VISIBLE);

CREATE TABLE `orders` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_user` INT UNSIGNED NOT NULL,
  `status` INT UNSIGNED NOT NULL,
  `delivery_type` INT UNSIGNED NOT NULL,
  `delivery_details` BIGINT UNSIGNED NULL,
  `price` FLOAT NOT NULL,
  `delivery_price` FLOAT NULL,
  `promo` BIGINT UNSIGNED NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_status_idx` (`status` ASC) VISIBLE,
  INDEX `fk_orders_promo_idx` (`promo` ASC) VISIBLE,
  CONSTRAINT `fk_orders_status`
    FOREIGN KEY (`status`)
    REFERENCES `order_status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_orders_promo`
    FOREIGN KEY (`promo`)
    REFERENCES `promo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `order_items` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_order` BIGINT UNSIGNED NOT NULL,
  `id_product` INT UNSIGNED NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  `price_per_product` FLOAT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_items_order_id_idx` (`id_order` ASC) VISIBLE,
  CONSTRAINT `fk_order_items_order_id`
    FOREIGN KEY (`id_order`)
    REFERENCES `orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
