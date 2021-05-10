package ru.geekbrains.spring.market;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.market.model.DeliveryPriceConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Data
@Component
public class DeliveryPrice {
    private DeliveryPriceConfig priceConfig;

    public DeliveryPrice() {
        try {
            File resource = new ClassPathResource("deliveryPrice.json").getFile();
            String priceJson = new String(Files.readAllBytes(resource.toPath()));
            priceConfig = new ObjectMapper().readValue(priceJson, DeliveryPriceConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
