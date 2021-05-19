package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderInfoDto {
    private Long id;
    private String orderStatus;
    private DeliveryInfoResponseDto deliveryInfoResponseDto;
    private Double price;
    private Double deliveryPrice;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;

    public OrderInfoDto(Order order) {
        this.id = order.getId();
        this.price = order.getPrice();
        this.deliveryPrice = order.getDeliveryPrice();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = order.getCreatedAt();
        for (OrderStatus o : OrderStatus.values()) {
            if (o.getStatus() == order.getOrderStatus()) {
                this.orderStatus = o.getName();
            }
        }
    }
}
