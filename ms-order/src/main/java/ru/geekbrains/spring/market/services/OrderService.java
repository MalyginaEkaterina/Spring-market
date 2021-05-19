package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.market.exceptions.OrderNotFoundException;
import ru.geekbrains.spring.market.exceptions.PromoInvalidException;
import ru.geekbrains.spring.market.model.*;
import ru.geekbrains.spring.market.repositories.OrderRepository;
import ru.geekbrains.spring.market.repositories.PromoRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final Integer PROMO_APPLIED = 1;

    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketService basketService;

    public PromoDto applyPromo(PromoCodeDto promoRequestDto) {
        Promo promo = promoRepository.getPromo(promoRequestDto.getPromoCode());
        if (promo == null) {
            throw new PromoInvalidException("Promo code not available");
        }
        return new PromoDto(promo);
    }

    public PromoCodeDto generate(PromoDto promoDto) {
        Promo promo = new Promo(promoDto);
        String promoCode = generateRandomCode();
        promo.setPromoCode(promoCode);
        promoRepository.save(promo);
        return new PromoCodeDto(promoCode);
    }

    public String generateRandomCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Transactional
    public OrderResponseDto createOrder(Integer userId, OrderRequestDto orderRequestDto) {
        Order order = new Order(orderRequestDto);
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.CREATED.getStatus());
        if (orderRequestDto.getPromoId() != null) {
            Promo promo = promoRepository.getOne(orderRequestDto.getPromoId());
            order.setPromo(promo);
            promo.setIsApplied(PROMO_APPLIED);
            promoRepository.save(promo);
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(orderRepository.save(order).getId());
        basketService.deleteByIds(orderRequestDto.getItems().stream().map(FullBasketDto::getId).collect(Collectors.toList()));
        return orderResponseDto;
    }

    public Order getOrderInfo(Integer userId, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("There is no order with id " + id));
        if (order.getUserId() != userId) {
            throw new OrderNotFoundException("There is no order with id " + id);
        }
        return order;
    }

    public List<OrderBriefInfoDto> getOrders(Integer userId) {
        return orderRepository.findAllByUserId(userId).stream().map(OrderBriefInfoDto::new).collect(Collectors.toList());
    }
}
