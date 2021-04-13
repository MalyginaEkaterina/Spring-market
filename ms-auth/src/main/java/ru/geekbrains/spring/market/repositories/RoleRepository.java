package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.market.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}