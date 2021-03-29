package com.huawei.parkinglot.dao;

import com.huawei.parkinglot.entity.CheckInOut;
import com.huawei.parkinglot.entity.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInOutDAO extends JpaRepository<CheckInOut, Long> {

    CheckInOut findCheckInOutByVehicleAndCheckOutDateIsNull(Vehicle vehicle);

    Integer countCheckInOutByParkingAreaIdAndCheckOutDateIsNull(Long parkingAreaId);

    List<CheckInOut> findCheckInOutsByVehicleLicensePlate(String licensePlate);
}
