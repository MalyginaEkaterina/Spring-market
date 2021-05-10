package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class ShopDto {
    private Integer id;
    private String city;
    private String location;
    private String workHours;
    private String phone;
}
