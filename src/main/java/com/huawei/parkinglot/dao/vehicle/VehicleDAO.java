package com.huawei.parkinglot.dao.vehicle;

import com.huawei.parkinglot.entity.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleDAO extends JpaRepository<Vehicle, String> {
}
