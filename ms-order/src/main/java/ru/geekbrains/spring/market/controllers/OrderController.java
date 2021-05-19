package ru.geekbrains.spring.market.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.Const;
import ru.geekbrains.spring.market.DeliveryClient;
import ru.geekbrains.spring.market.ProductClient;
import ru.geekbrains.spring.market.StorageClient;
import ru.geekbrains.spring.market.configurations.jwt.JwtProvider;
import ru.geekbrains.spring.market.exceptions.NotEnoughProductsException;
import ru.geekbrains.spring.market.model.*;
import ru.geekbrains.spring.market.services.OrderService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private DeliveryClient deliveryClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private StorageClient storageClient;

    @PostMapping("/promo")
    public PromoDto applyPromo(@RequestBody PromoCodeDto promoCodeDto) {
        return orderService.applyPromo(promoCodeDto);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/promo/generate")
    public PromoCodeDto generatePromo(@RequestBody PromoDto promoDto) {
        return orderService.generate(promoDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public OrderResponseDto createOrder(@RequestHeader(Const.AUTHORIZATION) String token, @RequestBody OrderRequestDto orderRequestDto) {
        List<ProductItemDto> productItemDtoList = orderRequestDto.getItems().stream().map(fullBasketDto -> {
            ProductItemDto productItemDto = new ProductItemDto();
            productItemDto.setProductId(fullBasketDto.getProduct().getId());
            productItemDto.setQuantity(fullBasketDto.getQuantity());
            return productItemDto;
        }).collect(Collectors.toList());
        ProductReserveResponse reserveResponse = storageClient.reserveProducts(productItemDtoList);
        if (reserveResponse.getStatus() == Const.STATUS_OK) {
            return orderService.createOrder(jwtProvider.getUserIdFromToken(token.substring(7)), orderRequestDto);
        } else {
            throw new NotEnoughProductsException("Not enough products in storage", reserveResponse);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<OrderBriefInfoDto> getOrders(@RequestHeader(Const.AUTHORIZATION) String token) {
        return orderService.getOrders(jwtProvider.getUserIdFromToken(token.substring(7)));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public OrderInfoDto getOrderInfo(@RequestHeader(Const.AUTHORIZATION) String token, @PathVariable Long id) {
        Order order = orderService.getOrderInfo(jwtProvider.getUserIdFromToken(token.substring(7)), id);
        OrderInfoDto orderInfoDto = new OrderInfoDto(order);
        orderInfoDto.setDeliveryInfoResponseDto(deliveryClient
                .getDeliveryDetails(new DeliveryInfoRequestDto(order.getDeliveryType(), order.getDeliveryDetails())));

        List<OrderItem> orderItems = order.getItems();
        List<Integer> productIds = orderItems.stream().map(OrderItem::getProductId).collect(Collectors.toList());
        Map<Integer, ProductBasketDto> productBasketDtos = productClient
                .getProductsByIds(new ProductBasketRequestDto(productIds))
                .stream()
                .collect(Collectors.toMap(p -> p.getId(), p -> p));
        List<OrderItemDto> orderItemDtos = orderItems.stream().map(orderItem -> {
            OrderItemDto orderItemDto = new OrderItemDto(orderItem);
            orderItemDto.setProduct(productBasketDtos.get(orderItem.getProductId()));
            return orderItemDto;
        }).collect(Collectors.toList());
        orderInfoDto.setItems(orderItemDtos);
        return orderInfoDto;
    }
}
