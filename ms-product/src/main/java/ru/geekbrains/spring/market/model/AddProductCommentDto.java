package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class AddProductCommentDto {
    private Integer userId;
    private Integer productId;
    private Integer rating;
    private String comment;
}
