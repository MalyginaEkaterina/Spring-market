package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.market.model.UserDeliveryAddress;

public interface UserDeliveryAddressRepository extends JpaRepository<UserDeliveryAddress, Integer> {
}
