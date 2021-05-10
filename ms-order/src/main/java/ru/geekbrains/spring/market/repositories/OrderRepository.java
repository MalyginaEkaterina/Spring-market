package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.market.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
