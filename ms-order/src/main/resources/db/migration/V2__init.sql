ALTER TABLE `market_orders`.`orders`
ADD COLUMN `total_price` FLOAT NOT NULL AFTER `delivery_price`;
