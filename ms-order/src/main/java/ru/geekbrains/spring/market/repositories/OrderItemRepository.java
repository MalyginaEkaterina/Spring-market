package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.market.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
