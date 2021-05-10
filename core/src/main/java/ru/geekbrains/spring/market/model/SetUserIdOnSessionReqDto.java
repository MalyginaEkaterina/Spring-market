package ru.geekbrains.spring.market.model;

import lombok.Data;

import java.util.UUID;

@Data
public class SetUserIdOnSessionReqDto {
    private UUID guid;
    private Integer userId;
}
