CREATE TABLE `storages` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `location` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `products_items` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_product` INT UNSIGNED NOT NULL,
  `id_storage` INT UNSIGNED NOT NULL,
  `quantity` INT UNSIGNED NOT NULL,
  `place` VARCHAR(255) NULL COMMENT 'место на складе',
  PRIMARY KEY (`id`),
  INDEX `fk_products_items_storage_id_idx` (`id_storage` ASC) VISIBLE,
  CONSTRAINT `fk_products_items_storage_id`
    FOREIGN KEY (`id_storage`)
    REFERENCES `storages` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
