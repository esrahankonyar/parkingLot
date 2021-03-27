package com.huawei.parkinglot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "price_list_detail")
@Data
public class PriceListDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "hour")
    private String hour;

    @NotNull
    @Column(name = "price")
    private Double price;

    @NotNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "price_list_id", nullable = false)
    private PriceList priceListId;

}
