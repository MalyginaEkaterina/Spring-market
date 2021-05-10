package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.market.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
