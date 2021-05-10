package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PromoCodeDto {
    private String promoCode;

    public PromoCodeDto(String promoCode) {
        this.promoCode = promoCode;
    }
}
