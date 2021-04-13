package ru.geekbrains.spring.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.market.configurations.jwt.JwtProvider;
import ru.geekbrains.spring.market.model.*;
import ru.geekbrains.spring.market.services.UserService;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody SignUpRequestDto req) {
        User user = new User();
        user.setPassword(req.getPassword());
        user.setLogin(req.getLogin());
        user.setFio(req.getFio());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setRoles(new ArrayList<>());
        userService.saveUser(user, "USER");
        return "OK";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add_role")
    public String addRole(@RequestBody AddRoleRequestDto req) {
        User user = userService.findByLogin(req.getLogin());
        userService.saveUserRole(user, req.getRole());
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponseDto auth(@RequestBody AuthRequestDto req) {
        User user = userService.findByLoginAndPassword(req.getLogin(), req.getPassword());
        String token = jwtProvider.generateToken(user.getLogin(), user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()));
        return new AuthResponseDto(token);
    }
}