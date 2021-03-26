package com.huawei.parkinglot.service;

import com.huawei.parkinglot.entity.vehicle.Vehicle;

/**
 * Calculations will be placed on each vehicle type
 */
public interface VehicleService {

	void checkOut(Vehicle vehicle);
}
