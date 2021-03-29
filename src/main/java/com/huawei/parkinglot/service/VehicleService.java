package com.huawei.parkinglot.service;

import com.huawei.parkinglot.entity.CheckInOut;
import com.huawei.parkinglot.entity.vehicle.Vehicle;

import java.util.List;

/**
 * Calculations will be placed on each vehicle type
 */
public interface VehicleService {

	void checkIn(CheckInOut checkInOut);

	void checkOut(Vehicle vehicle);

	List<CheckInOut> getParkingDetails(String licensePlate);


}
