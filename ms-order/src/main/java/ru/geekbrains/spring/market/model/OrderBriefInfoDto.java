package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderBriefInfoDto {
    private Long id;
    private String orderStatus;
    private Double totalPrice;
    private LocalDateTime createdAt;

    public OrderBriefInfoDto(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = order.getCreatedAt();
        for (OrderStatus o : OrderStatus.values()) {
            if (o.getStatus() == order.getOrderStatus()) {
                this.orderStatus = o.getName();
            }
        }
    }
}
