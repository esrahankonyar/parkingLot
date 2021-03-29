package com.huawei.parkinglot.entity.vehicle;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
@DiscriminatorValue("SEDAN")
public class Sedan extends Vehicle{

}
