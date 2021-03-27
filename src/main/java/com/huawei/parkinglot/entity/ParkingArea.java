package com.huawei.parkinglot.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parking_area")
@Data
public class ParkingArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "capacity")
    private Integer capacity;

    @NotNull
    @Column(name = "city")
    private String city;


    @Valid
    @NotNull
    @JoinColumn(name = "price_list")
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private PriceList priceList;

}
