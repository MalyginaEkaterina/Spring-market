package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Column(name = "status")
    private Integer orderStatus;

    @Column(name = "delivery_type")
    private Integer deliveryType;

    @Column(name = "delivery_details")
    private Long deliveryDetails;

    @Column
    private Double price;

    @Column(name = "delivery_price")
    private Double deliveryPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne
    @JoinColumn(name = "promo")
    private Promo promo;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    List<OrderItem> items;

    public Order(OrderRequestDto orderRequestDto) {
        this.deliveryType = orderRequestDto.getDeliveryType();
        this.deliveryDetails = orderRequestDto.getDeliveryDetails();
        this.price = orderRequestDto.getPrice();
        this.deliveryPrice = orderRequestDto.getDeliveryPrice();
        this.totalPrice = orderRequestDto.getPrice() + orderRequestDto.getDeliveryPrice();
        this.items = orderRequestDto.getItems().stream().map(fullBasketDto -> {
            OrderItem orderItem = new OrderItem(fullBasketDto);
            orderItem.setOrder(this);
            return orderItem;
        }).collect(Collectors.toList());
    }
}
