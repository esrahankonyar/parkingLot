package com.huawei.parkinglot.entity.vehicle;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
@DiscriminatorValue("SUV")
public class Suv extends Vehicle {

}
