package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class UserDeliveryAddressDto {
    private String city;
    private String street;
    private String house;
    private String postalCode;
    private String apt;
    private String addInfo;
}
