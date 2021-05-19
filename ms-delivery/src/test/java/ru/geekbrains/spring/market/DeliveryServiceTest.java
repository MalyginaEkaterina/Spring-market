package ru.geekbrains.spring.market;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.spring.market.exceptions.ShopNotFoundException;
import ru.geekbrains.spring.market.model.*;
import ru.geekbrains.spring.market.repositories.DeliveryRedisRepository;
import ru.geekbrains.spring.market.repositories.PickUpPointRepository;
import ru.geekbrains.spring.market.repositories.ShopRepository;
import ru.geekbrains.spring.market.services.DeliveryService;

import java.util.*;

@SpringBootTest(classes = DeliveryService.class)
public class DeliveryServiceTest {

    @Autowired
    private DeliveryService deliveryService;

    @MockBean
    private ShopRepository shopRepository;

    @MockBean
    private DeliveryRedisRepository deliveryRedisRepository;

    @MockBean
    private PickUpPointRepository pickUpPointRepository;

    @MockBean
    private DeliveryPrice deliveryPrice;

    @Test
    public void testGetAllShopsFromRedis() {
        List<Shop> shops = new ArrayList<>();
        Shop shop1 = new Shop();
        shop1.setId(1L);
        shop1.setCity("one");
        shop1.setLocation("location1");

        Shop shop2 = new Shop();
        shop2.setId(2L);
        shop2.setCity("two");
        shop2.setLocation("location2");

        shops.add(shop1);
        shops.add(shop2);

        Mockito
                .doReturn(shops)
                .when(deliveryRedisRepository)
                .getShops();

        List<Shop> res = deliveryService.getAllShops();
        Mockito.verify(deliveryRedisRepository, Mockito.times(1)).getShops();
        Assertions.assertEquals(2, res.size());
        Assertions.assertEquals("two", res.get(1).getCity());
    }

    @Test
    public void testGetAllShopsNull() {
        Mockito
                .doReturn(null)
                .when(deliveryRedisRepository)
                .getShops();

        Mockito
                .doReturn(new ArrayList<Shop>())
                .when(shopRepository)
                .findAll();

        List<Shop> res = deliveryService.getAllShops();
        Mockito.verify(deliveryRedisRepository, Mockito.times(0)).putShops(res);
        Assertions.assertEquals(0, res.size());
    }

    @Test
    public void testGetAllShopsFromDB() {
        List<Shop> shops = new ArrayList<>();
        Shop shop1 = new Shop();
        shop1.setId(1L);
        shop1.setCity("one");
        shop1.setLocation("location1");

        Shop shop2 = new Shop();
        shop2.setId(2L);
        shop2.setCity("two");
        shop2.setLocation("location2");

        shops.add(shop1);
        shops.add(shop2);

        Mockito
                .doReturn(null)
                .when(deliveryRedisRepository)
                .getShops();

        Mockito
                .doReturn(shops)
                .when(shopRepository)
                .findAll();

        List<Shop> res = deliveryService.getAllShops();
        Mockito.verify(shopRepository, Mockito.times(1)).findAll();
        Mockito.verify(deliveryRedisRepository, Mockito.times(1)).putShops(ArgumentMatchers.eq(shops));
        Assertions.assertEquals(2, res.size());
        Assertions.assertEquals("two", res.get(1).getCity());
    }

    @Test
    public void testGetAllPointsFromRedis() {
        List<PickUpPoint> points = new ArrayList<>();
        PickUpPoint point1 = new PickUpPoint();
        point1.setId(1L);
        point1.setCity("one");
        point1.setLocation("location1");

        PickUpPoint point2 = new PickUpPoint();
        point2.setId(2L);
        point2.setCity("two");
        point2.setLocation("location2");

        points.add(point1);
        points.add(point2);

        Mockito
                .doReturn(points)
                .when(deliveryRedisRepository)
                .getPickUpPoints();

        List<PickUpPoint> res = deliveryService.getAllPoints();
        Mockito.verify(deliveryRedisRepository, Mockito.times(1)).getPickUpPoints();
        Assertions.assertEquals(2, res.size());
        Assertions.assertEquals("two", res.get(1).getCity());
    }

    @Test
    public void testGetAllPointsNull() {
        Mockito
                .doReturn(null)
                .when(deliveryRedisRepository)
                .getPickUpPoints();

        Mockito
                .doReturn(new ArrayList<PickUpPoint>())
                .when(pickUpPointRepository)
                .findAll();

        List<PickUpPoint> res = deliveryService.getAllPoints();
        Mockito.verify(deliveryRedisRepository, Mockito.times(0)).putPickUpPoints(res);
        Assertions.assertEquals(0, res.size());
    }

    @Test
    public void testGetAllPointsFromDB() {
        List<PickUpPoint> points = new ArrayList<>();
        PickUpPoint point1 = new PickUpPoint();
        point1.setId(1L);
        point1.setCity("one");
        point1.setLocation("location1");

        PickUpPoint point2 = new PickUpPoint();
        point2.setId(2L);
        point2.setCity("two");
        point2.setLocation("location2");

        points.add(point1);
        points.add(point2);

        Mockito
                .doReturn(null)
                .when(deliveryRedisRepository)
                .getPickUpPoints();

        Mockito
                .doReturn(points)
                .when(pickUpPointRepository)
                .findAll();

        List<PickUpPoint> res = deliveryService.getAllPoints();
        Mockito.verify(pickUpPointRepository, Mockito.times(1)).findAll();
        Mockito.verify(deliveryRedisRepository, Mockito.times(1)).putPickUpPoints(ArgumentMatchers.eq(points));
        Assertions.assertEquals(2, res.size());
        Assertions.assertEquals("two", res.get(1).getCity());
    }

    @Test
    public void testGetShop() {
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setCity("one");
        shop.setLocation("location1");

        Mockito
                .doReturn(Optional.of(shop))
                .when(shopRepository)
                .findById(1L);

        Shop res = deliveryService.getShop(1L);
        Mockito.verify(shopRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(1L));
        Assertions.assertEquals("one", res.getCity());
    }

    @Test
    public void testGetShopNull() {
        Mockito
                .doReturn(Optional.empty())
                .when(shopRepository)
                .findById(1L);

        Assertions.assertThrows(ShopNotFoundException.class, () -> {
            deliveryService.getShop(1L);
        });
        Mockito.verify(shopRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(1L));
    }

    @Test
    public void testGetPickUpPoint() {
        PickUpPoint point1 = new PickUpPoint();
        point1.setId(1L);
        point1.setCity("one");
        point1.setLocation("location1");

        Mockito
                .doReturn(Optional.of(point1))
                .when(pickUpPointRepository)
                .findById(1L);

        PickUpPoint res = deliveryService.getPickUpPoint(1L);
        Mockito.verify(pickUpPointRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(1L));
        Assertions.assertEquals("one", res.getCity());
    }

    @Test
    public void testGetPickUpPointNull() {
        Mockito
                .doReturn(Optional.empty())
                .when(pickUpPointRepository)
                .findById(1L);

        Assertions.assertThrows(ShopNotFoundException.class, () -> {
            deliveryService.getPickUpPoint(1L);
        });
        Mockito.verify(pickUpPointRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(1L));
    }

    @Test
    public void testGetDeliveryPrice() {
        List<DeliveryPriceConditions> priceConditions = new ArrayList<>();
        DeliveryPriceConditions condition1 = new DeliveryPriceConditions();
        condition1.setMinTotalPrice(0);
        condition1.setPrice(100);

        DeliveryPriceConditions condition2 = new DeliveryPriceConditions();
        condition2.setMinTotalPrice(100);
        condition2.setPrice(50);

        priceConditions.add(condition1);
        priceConditions.add(condition2);

        Map<String, List<DeliveryPriceConditions>> confMap = new HashMap<>();
        confMap.put("COURIER", priceConditions);

        DeliveryPriceConfig config = new DeliveryPriceConfig();
        config.setPriceConfig(confMap);

        Mockito
                .doReturn(config)
                .when(deliveryPrice)
                .getPriceConfig();

        DeliveryPriceRequestDto req1 = new DeliveryPriceRequestDto();
        req1.setDeliveryType("COURIER");
        req1.setTotalPrice(50f);

        DeliveryPriceRequestDto req2 = new DeliveryPriceRequestDto();
        req2.setDeliveryType("COURIER");
        req2.setTotalPrice(100f);

        DeliveryPriceRequestDto req3 = new DeliveryPriceRequestDto();
        req3.setDeliveryType("COURIER");
        req3.setTotalPrice(1000f);

        DeliveryPriceResponseDto resp1 = deliveryService.getDeliveryPrice(req1);
        DeliveryPriceResponseDto resp2 = deliveryService.getDeliveryPrice(req2);
        DeliveryPriceResponseDto resp3 = deliveryService.getDeliveryPrice(req3);
        Assertions.assertEquals(100, resp1.getPrice());
        Assertions.assertEquals(50, resp2.getPrice());
        Assertions.assertEquals(50, resp3.getPrice());
    }
}
