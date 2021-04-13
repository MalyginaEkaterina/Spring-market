package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketDto {
    private Long id;
    private Integer productId;
    private Integer quantity;

    public BasketDto(Basket basketProduct) {
        this.id = basketProduct.getId();
        this.productId = basketProduct.getProductId();
        this.quantity = basketProduct.getQuantity();
    }
}
