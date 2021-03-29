package com.huawei.parkinglot.service;

import com.huawei.parkinglot.entity.CheckInOut;
import com.huawei.parkinglot.entity.vehicle.Vehicle;

import java.util.List;
import java.util.concurrent.TimeUnit;


public interface VehicleService {

	void checkIn(CheckInOut checkInOut);

	void checkOut(Vehicle vehicle);

	List<CheckInOut> getParkingDetails(String licensePlate);

	default Double diffHours(Long checkInTime, Long checkOutTime){

		Long diffMinutes = checkInTime - checkOutTime;
		diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMinutes);
		return  (double) diffMinutes / 60;

	}


}
