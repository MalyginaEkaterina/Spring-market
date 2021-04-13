package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.spring.market.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}