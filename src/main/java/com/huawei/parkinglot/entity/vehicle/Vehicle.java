package com.huawei.parkinglot.entity.vehicle;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
//TODO define inheritance
public class Vehicle {

	@Id
	private String licensePlate;
	
	//TODO getters setters
}
