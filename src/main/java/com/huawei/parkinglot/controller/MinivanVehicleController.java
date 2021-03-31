package com.huawei.parkinglot.controller;

import com.huawei.parkinglot.entity.CheckInOut;
import com.huawei.parkinglot.entity.vehicle.Vehicle;
import com.huawei.parkinglot.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/minivan", produces = MediaType.APPLICATION_JSON_VALUE)
public class MinivanVehicleController implements VehicleController {

    @Autowired
    @Qualifier("minivanVehicleService")
    VehicleService vehicleService;

    @Override
    @PostMapping(value = "/checkIn")
    public void checkIn(@Valid @RequestBody CheckInOut checkInOut) {
        vehicleService.checkIn(checkInOut);
    }

    @Override
    @PutMapping(value = "/checkOut")
    public void checkOut(@Valid @RequestBody Vehicle vehicle) {
        vehicleService.checkOut(vehicle);
    }

    @Override
    @GetMapping(value = "/getParkingDetails")
    public List<CheckInOut> getParkingDetails(@RequestParam String licensePlate) {
        return vehicleService.getParkingDetails(licensePlate);
    }
}
