package com.huawei.parkinglot.controller;

import com.huawei.parkinglot.entity.CheckInOut;
import com.huawei.parkinglot.entity.vehicle.Vehicle;

import java.util.List;

public interface VehicleController {

    void checkIn(CheckInOut checkInOut);
    void checkOut(Vehicle vehicle);
    List<CheckInOut> getParkingDetails(String licensePlate);

}
