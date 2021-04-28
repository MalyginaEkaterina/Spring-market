package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.market.model.DeliveryType;

public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Integer> {
}
