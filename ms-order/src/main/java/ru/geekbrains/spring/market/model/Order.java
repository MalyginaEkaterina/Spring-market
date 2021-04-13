package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "id_user")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "status")
    private OrderStatus orderStatus;

    @Column(name = "delivery_type")
    private Integer deliveryType;

    @Column(name = "delivery_details")
    private Long deliveryDetails;

    @Column
    private Double price;

    @Column(name = "delivery_price")
    private Double deliveryPrice;

    @OneToOne
    @JoinColumn(name = "promo")
    private Promo promo;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order")
    List<OrderItem> items;

}
