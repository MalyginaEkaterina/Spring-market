package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PromoDto {
    private Long id;
    private Double discountAbs;
    private Integer discountPercent;
    private Double minOrderPrice;
    private Integer expiryDays;

    public PromoDto(Promo promo) {
        this.id = promo.getId();
        this.discountAbs = promo.getDiscountAbs();
        this.discountPercent = promo.getDiscountPercent();
        this.minOrderPrice = promo.getMinOrderPrice();
    }
}
