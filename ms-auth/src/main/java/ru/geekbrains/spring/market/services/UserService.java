package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.exceptions.IncorrectParamException;
import ru.geekbrains.spring.market.model.Role;
import ru.geekbrains.spring.market.model.User;
import ru.geekbrains.spring.market.repositories.RoleRepository;
import ru.geekbrains.spring.market.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user, String r) {
        Role role = roleRepository.findByName("ROLE_" + r.toUpperCase());
        if (role == null) {
            throw new IncorrectParamException("There is no role " + r);
        }
        user.addRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User saveUserRole(User user, String r) {
        Role role = roleRepository.findByName("ROLE_" + r.toUpperCase());
        if (role == null) {
            throw new IncorrectParamException("There is no role " + r);
        }
        user.addRole(role);
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}