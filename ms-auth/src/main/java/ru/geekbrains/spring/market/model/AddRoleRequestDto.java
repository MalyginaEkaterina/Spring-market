package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class AddRoleRequestDto {
    private String login;
    private String role;
}
