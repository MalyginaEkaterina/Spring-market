package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "promo")
@Data
@NoArgsConstructor
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "exp_date")
    private LocalDateTime expDate;

    @Column(name = "discount_abs")
    private Double discountAbs;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "min_order_price")
    private Double minOrderPrice;

    @Column(name = "is_applied")
    private Integer isApplied;
}
