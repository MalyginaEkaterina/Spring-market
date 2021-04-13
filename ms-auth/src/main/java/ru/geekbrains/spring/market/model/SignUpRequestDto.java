package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String login;
    private String password;
    private String fio;
    private String phone;
    private String email;
}