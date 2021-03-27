package com.huawei.parkinglot.service;

import com.huawei.parkinglot.entity.ParkingArea;

import java.util.Date;
import java.util.List;

public interface ParkingAreaService {

    public ParkingArea createParkingArea(ParkingArea parkingArea);

    public ParkingArea updateParkingArea(ParkingArea parkingArea);

    public void deleteParkingArea(Long id);

    public List<ParkingArea> getParkingAreaByName(String name);

    public Double getDailyIncomeOfParkingArea(Date date);


}
