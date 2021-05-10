package ru.geekbrains.spring.market.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserInfoDto {
    private String login;
    private String fio;
    private String phone;
    private String email;
    private List<UserDeliveryAddressDto> addresses;

//    public UserInfoDto(User user) {
//        this.login = user.getLogin();
//        this.fio = user.getFio();
//        this.phone = user.getPhone();
//        this.email = user.getEmail();
//        this.addresses = user.getAddresses().stream().map(UserDeliveryAddressDto::new).collect(Collectors.toList());
//    }
}
