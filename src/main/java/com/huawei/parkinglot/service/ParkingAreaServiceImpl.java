package com.huawei.parkinglot.service;

import com.huawei.parkinglot.constants.ErrorMessages;
import com.huawei.parkinglot.dao.ParkingAreaDAO;
import com.huawei.parkinglot.entity.ParkingArea;
import com.huawei.parkinglot.entity.PriceListDetail;
import com.huawei.parkinglot.exceptions.RestApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ParkingAreaServiceImpl implements ParkingAreaService {

    @Autowired
    ParkingAreaDAO parkingAreaDAO;

    @Override
    public ParkingArea createParkingArea(ParkingArea parkingArea) {
        if (!totalHoursIs24(parkingArea.getPriceList().getPriceListDetails())) {
            throw new RestApiRequestException(ErrorMessages.NOT_SUITABLE_HOURS);
        }
        return parkingAreaDAO.save(parkingArea);
    }

    @Override
    public ParkingArea updateParkingArea(ParkingArea parkingArea) {
        return parkingAreaDAO.saveAndFlush(parkingArea);
    }

    @Override
    public void deleteParkingArea(Long id) {
        parkingAreaDAO.deleteById(id);
    }

    @Override
    public List<ParkingArea> getParkingAreaByName(String name) {
        return parkingAreaDAO.findParkingAreasByName(name);
    }

    private boolean totalHoursIs24(List<PriceListDetail> priceList) {
        int totalHour = 0;
        for (PriceListDetail detail : priceList) {
            String[] hours = detail.getHour().split("-");
            totalHour += Math.abs(Integer.parseInt(hours[0]) - Integer.parseInt(hours[1]));
        }
        return totalHour == 24;
    }
}
