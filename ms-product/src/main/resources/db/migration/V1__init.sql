CREATE TABLE `products` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `picture_url` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `products` (`title`, `price`, `created_at`, `updated_at`) VALUES
('one', '110', now(), now()),
('two', '220', now(), now()),
('three', '330', now(), now()),
('four', '440', now(), now()),
('five', '550', now(), now());

CREATE TABLE `categories` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `parent_category` INT UNSIGNED NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `categories`
  ADD INDEX `fk_parent_category_id_idx` (`parent_category` ASC) VISIBLE;
  ;
  ALTER TABLE `categories`
  ADD CONSTRAINT `fk_parent_category_id`
    FOREIGN KEY (`parent_category`)
    REFERENCES `categories` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE;


INSERT INTO `categories` (`name`) VALUES
('cat_1'),
('cat_2');

CREATE TABLE `products_categories` (
  `id_product` INT UNSIGNED NOT NULL,
  `id_category` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id_product`, `id_category`),
  INDEX `fk_prod_cat_category_id_idx` (`id_category` ASC) VISIBLE,
  CONSTRAINT `fk_prod_cat_product_id`
    FOREIGN KEY (`id_product`)
    REFERENCES `products` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_prod_cat_category_id`
    FOREIGN KEY (`id_category`)
    REFERENCES `categories` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT);

CREATE TABLE `products_comments` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_product` INT UNSIGNED NOT NULL,
  `id_user` INT UNSIGNED NOT NULL,
  `rating` TINYINT NOT NULL,
  `comment` VARCHAR(1024) NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_products_comments_product_id_idx` (`id_product` ASC) VISIBLE,
  CONSTRAINT `fk_products_comments_product_id`
    FOREIGN KEY (`id_product`)
    REFERENCES `products` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);