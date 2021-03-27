package com.huawei.parkinglot.dao;

import com.huawei.parkinglot.entity.ParkingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingAreaDAO extends JpaRepository<ParkingArea, Long> {

    List<ParkingArea> findParkingAreasByName(String name);

}
