package com.huawei.parkinglot.entity.vehicle;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "vehicle")
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Vehicle {

    @Id
    private String licensePlate;

}
