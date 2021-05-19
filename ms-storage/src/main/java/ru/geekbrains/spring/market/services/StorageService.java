package ru.geekbrains.spring.market.services;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.Const;
import ru.geekbrains.spring.market.model.ProductItem;
import ru.geekbrains.spring.market.model.ProductItemDto;
import ru.geekbrains.spring.market.model.ProductReserveResponse;
import ru.geekbrains.spring.market.repositories.StorageRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;

    public ProductReserveResponse reserveProducts(List<ProductItemDto> listProductItemDto) {
        Map<Integer, Integer> reservedProducts = listProductItemDto.stream().collect(Collectors.toMap(p -> p.getProductId(), p -> p.getQuantity()));
        List<ProductItem> productItems = storageRepository.getByProductIds(Lists.newArrayList(reservedProducts.keySet()));
        ProductReserveResponse productReserveResponse = new ProductReserveResponse();
        for (ProductItem p : productItems) {
            if (p.getQuantity() >= reservedProducts.get(p.getProductId())) {
                p.setQuantity(p.getQuantity() - reservedProducts.get(p.getProductId()));
            } else {
                ProductItemDto productItemDto = new ProductItemDto(p.getProductId(), p.getQuantity());
                productReserveResponse.addMaxQuantities(productItemDto);
            }
        }
        if (productReserveResponse.getMaxQuantities().isEmpty()) {
            productReserveResponse.setStatus(Const.STATUS_OK);
            for (ProductItem p : productItems) {
                storageRepository.save(p);
            }
        } else {
            productReserveResponse.setStatus(Const.STATUS_ERROR);
        }
        return productReserveResponse;
    }
}
