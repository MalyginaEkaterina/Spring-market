package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_delivery_addresses")
@Data
@NoArgsConstructor
public class UserDeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public UserDeliveryAddress(UserDeliveryAddressDto userDeliveryAddressDto) {
        this.city = userDeliveryAddressDto.getCity();
        this.street = userDeliveryAddressDto.getStreet();
        this.house = userDeliveryAddressDto.getHouse();
        this.postalCode = userDeliveryAddressDto.getPostalCode();
        this.apt = userDeliveryAddressDto.getApt();
        this.addInfo = userDeliveryAddressDto.getAddInfo();
    }
}
