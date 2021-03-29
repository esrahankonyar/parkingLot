package com.huawei.parkinglot.service;

import com.huawei.parkinglot.constants.ErrorMessages;
import com.huawei.parkinglot.dao.CheckInOutDAO;
import com.huawei.parkinglot.dao.ParkingAreaDAO;
import com.huawei.parkinglot.dao.vehicle.VehicleDAO;
import com.huawei.parkinglot.entity.CheckInOut;
import com.huawei.parkinglot.entity.ParkingArea;
import com.huawei.parkinglot.entity.PriceListDetail;
import com.huawei.parkinglot.entity.vehicle.Suv;
import com.huawei.parkinglot.entity.vehicle.Vehicle;
import com.huawei.parkinglot.exceptions.RestApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SuvVehicleService implements VehicleService {

	@Autowired
	CheckInOutDAO checkInOutDAO;

	@Autowired
	VehicleDAO suvDao;

	@Autowired
	ParkingAreaDAO parkingAreaDAO;

	@Override
	public void checkIn(CheckInOut checkInOut) {

		CheckInOut isExistCheckIn = checkInOutDAO.findCheckInOutByVehicleAndCheckOutDateIsNull(checkInOut.getVehicle());

		if (isExistCheckIn != null) {
			throw new RestApiRequestException(ErrorMessages.CHECK_IN_IS_ALREADY_EXIST);
		}
		Optional<ParkingArea> parkingArea = parkingAreaDAO.findById(checkInOut.getParkingArea().getId());
		if(!parkingArea.isPresent()){
			throw new RestApiRequestException(ErrorMessages.PARKING_AREA_IS_NOT_FOUND);
		}
		Integer parkingAreaCapacity = parkingArea.get().getCapacity();
		int checkInCountInParkingArea = checkInOutDAO.countCheckInOutByParkingAreaIdAndCheckOutDateIsNull(checkInOut.getParkingArea().getId());

		if(parkingAreaCapacity == checkInCountInParkingArea){
			throw new RestApiRequestException(ErrorMessages.FULL_CAPACITY_IN_PARKING_AREA);
		}

		checkInOut.setVehicle(vehicleRecordControl(checkInOut.getVehicle().getLicensePlate()));
		checkInOutDAO.saveAndFlush(checkInOut);
	}

	@Override
	public void checkOut(Vehicle vehicle) {
		CheckInOut checkInOut = checkInOutDAO.findCheckInOutByVehicleAndCheckOutDateIsNull(vehicle);
		checkInOut.setCheckOutDate(new Timestamp(System.currentTimeMillis()));
		if (checkInOut.getVehicle() instanceof Suv) {
			Double diffHours = diffHours(checkInOut.getCheckOutDate().getTime(), checkInOut.getCheckInDate().getTime());
			for (PriceListDetail priceListDetail : checkInOut.getParkingArea().getPriceList().getPriceListDetails()) {
				String[] hours = priceListDetail.getHour().split("-");
				if (Integer.parseInt(hours[0]) <= diffHours && Integer.parseInt(hours[1]) >= diffHours) {
					checkInOut.setFee(priceListDetail.getPrice()*1.10);
					break;
				}
			}
		}

		checkInOutDAO.saveAndFlush(checkInOut);

	}

	@Override
	public List<CheckInOut> getParkingDetails(String licensePlate) {
		return checkInOutDAO.findCheckInOutsByVehicleLicensePlate(licensePlate);
	}

	private Vehicle vehicleRecordControl(String licencePlate) {
		Optional<Vehicle> vehicle = suvDao.findById(licencePlate);

		if (vehicle.isEmpty()) {
			Suv suv = new Suv();
			suv.setLicensePlate(licencePlate);
			return suvDao.save(suv);
		}

		return vehicle.get();
	}
}
