package com.huawei.parkinglot.controller;

import com.huawei.parkinglot.entity.ParkingArea;
import com.huawei.parkinglot.service.ParkingAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/parkingArea")
public class ParkingAreaController {

    @Autowired
    ParkingAreaService parkingAreaService;

    @PostMapping(value = {"/createParkingArea"})
    public ParkingArea createParkingArea(@Valid @RequestBody ParkingArea parkingArea) {
        return parkingAreaService.createParkingArea(parkingArea);
    }

    @PutMapping(value = {"/updateParkingArea"})
    public ParkingArea updateParkingArea(@Valid @RequestBody ParkingArea parkingArea) {
        return parkingAreaService.updateParkingArea(parkingArea);
    }

    @DeleteMapping(value = {"/deleteParkingArea/{id}"})
    public void deleteParkingArea(@RequestParam Long id) {
        parkingAreaService.deleteParkingArea(id);
    }

    @GetMapping(value = {"/getParkinAreaByName/{name}"})
    public List<ParkingArea> getParkinAreaByName(@RequestParam String name) {
        return parkingAreaService.getParkingAreaByName(name);
    }


}
