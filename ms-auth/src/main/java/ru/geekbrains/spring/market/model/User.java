package ru.geekbrains.spring.market.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String fio;

    @Column
    private String phone;

    @Column
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    List<UserDeliveryAddress> addresses;

    public void addRole(Role r) {
        roles.add(r);
    }
}