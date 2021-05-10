package ru.geekbrains.spring.market.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.Const;
import ru.geekbrains.spring.market.configurations.MapperUtil;
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

    @Autowired
    private ModelMapper modelMapper;

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
        String token = jwtProvider.generateToken(user.getId(), user.getLogin(), user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()));
        return new AuthResponseDto(token);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user_logout")
    public String logout(@RequestHeader(Const.AUTHORIZATION) String token) {
        jwtProvider.setTokenLogout(token.substring(7));
        return "OK";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add_user_address")
    public String addUserAddress(@RequestHeader(Const.AUTHORIZATION) String token, @RequestBody UserDeliveryAddressDto address) {
        Integer userId = jwtProvider.getUserIdFromToken(token.substring(7));
        userService.addAddress(address, userId);
        return "OK";
    }
}
