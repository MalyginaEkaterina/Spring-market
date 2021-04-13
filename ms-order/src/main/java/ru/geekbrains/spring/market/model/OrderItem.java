package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    @Column(name = "id_product")
    private Integer productId;

    @Column
    private Integer quantity;

    @Column(name = "price_per_product")
    private Double pricePerProduct;
}
