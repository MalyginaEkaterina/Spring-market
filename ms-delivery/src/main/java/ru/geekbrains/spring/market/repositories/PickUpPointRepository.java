package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.market.model.PickUpPoint;

public interface PickUpPointRepository extends JpaRepository<PickUpPoint, Long> {
}
