package ru.geekbrains.spring.market.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_delivery_addresses")
@Data
public class UserDeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String house;

    @Column(name = "postal_code")
    private String postalCode;

    @Column
    private String apt;

    @Column(name = "add_info")
    private String addInfo;
}
