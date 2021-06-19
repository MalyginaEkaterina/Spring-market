package ru.geekbrains.spring.market.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.market.exceptions.ShopSqlException;
import ru.geekbrains.spring.market.model.Shop;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@PropertySource("classpath:application.yaml")
public class ShopMapperRepo {
    private Connection connection;

    @Autowired
    public ShopMapperRepo(@Value("${db.url}") String connString, @Value("${db.driver}") String driver,
                          @Value("${db.username}") String dbUser, @Value("${db.password}") String dbPass) {
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(connString, dbUser, dbPass);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Shop> findAll() {
        List<Shop> result = new ArrayList<>();
        try {
            String select = "SELECT * FROM market_delivery.shops";
            PreparedStatement pstmt = connection.prepareStatement(select);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Shop shop = new Shop(rs.getLong("id"), rs.getString("city"), rs.getString("location"),
                        rs.getString("work_hours"), rs.getString("phone"));
                result.add(shop);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ShopSqlException(e.getMessage());
        }
    }

    public Optional<Shop> findById(Long id) {
        try {
            String select = "SELECT * FROM market_delivery.shops WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(select);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            Shop shop = null;
            if (rs.next()) {
                shop = new Shop(rs.getLong("id"), rs.getString("city"), rs.getString("location"),
                        rs.getString("work_hours"), rs.getString("phone"));
            }
            return Optional.ofNullable(shop);
        } catch (SQLException e) {
            throw new ShopSqlException(e.getMessage());
        }
    }

    public Shop save(Shop shop) {
        if (shop.getId() != null) {
            try {
                String update = "UPDATE market_delivery.shops SET `city` = ?, `location` = ?, `work_hours` = ?, `phone` = ? WHERE id = ?";
                PreparedStatement pstmt = connection.prepareStatement(update);
                pstmt.setString(1, shop.getCity());
                pstmt.setString(2, shop.getLocation());
                pstmt.setString(3, shop.getWorkHours());
                pstmt.setString(4, shop.getPhone());
                pstmt.setLong(5, shop.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new ShopSqlException(e.getMessage());
            }
        } else {
            try {
                String insert = "INSERT INTO market_delivery.shops (`city`, `location`, `work_hours`, `phone`) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, shop.getCity());
                pstmt.setString(2, shop.getLocation());
                pstmt.setString(3, shop.getWorkHours());
                pstmt.setString(4, shop.getPhone());
                pstmt.executeUpdate();

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        shop.setId(generatedKeys.getLong(1));
                    } else {
                        throw new ShopSqlException("insert into shops was failed");
                    }
                } catch (Exception e) {
                    throw new ShopSqlException(e.getMessage());
                }
            } catch (SQLException e) {
                throw new ShopSqlException(e.getMessage());
            }
        }
        return shop;
    }
}
