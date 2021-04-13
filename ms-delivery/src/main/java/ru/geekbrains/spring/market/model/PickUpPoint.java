package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pick_up_points")
@Data
@NoArgsConstructor
public class PickUpPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String city;

    @Column
    private String location;

    @Column(name = "work_hours")
    private String workHours;

}
