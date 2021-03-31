package com.huawei.parkinglot.service;

import com.huawei.parkinglot.ParkinglotApplication;
import com.huawei.parkinglot.constants.ErrorMessages;
import com.huawei.parkinglot.dao.CheckInOutDAO;
import com.huawei.parkinglot.dao.ParkingAreaDAO;
import com.huawei.parkinglot.dao.vehicle.VehicleDAO;
import com.huawei.parkinglot.entity.CheckInOut;
import com.huawei.parkinglot.entity.ParkingArea;
import com.huawei.parkinglot.entity.PriceList;
import com.huawei.parkinglot.entity.PriceListDetail;
import com.huawei.parkinglot.entity.vehicle.Vehicle;
import com.huawei.parkinglot.exceptions.RestApiRequestException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkinglotApplication.class})
public class MinivanVehicleServiceTest {

    @InjectMocks
    private MinivanVehicleService service;

    @Autowired
    private VehicleDAO minivanDAO;

    @Autowired
    private CheckInOutDAO checkInOutDAO;

    @Autowired
    private ParkingAreaDAO parkingAreaDAO;

    private ParkingArea parkingArea;
    private CheckInOut checkInOut;
    private Vehicle minivan;


    @Before
    public void setup() throws ParseException {
        service.setMinivanDao(minivanDAO);
        service.setCheckInOutDAO(checkInOutDAO);
        service.setParkingAreaDAO(parkingAreaDAO);

        parkingArea = new ParkingArea();
        PriceList priceList = new PriceList();
        List<PriceListDetail> priceListDetails = new ArrayList<>();

        PriceListDetail priceListDetailOne = new PriceListDetail();
        priceListDetailOne.setHour("0-2");
        priceListDetailOne.setPrice(10D);
        priceListDetailOne.setPriceListId(priceList);

        PriceListDetail priceListDetailTwo = new PriceListDetail();
        priceListDetailTwo.setHour("2-4");
        priceListDetailTwo.setPrice(12D);
        priceListDetailTwo.setPriceListId(priceList);

        PriceListDetail priceListDetailThree = new PriceListDetail();
        priceListDetailThree.setHour("4-8");
        priceListDetailThree.setPrice(14D);
        priceListDetailThree.setPriceListId(priceList);

        PriceListDetail priceListDetailFour = new PriceListDetail();
        priceListDetailFour.setHour("8-14");
        priceListDetailFour.setPrice(16D);
        priceListDetailFour.setPriceListId(priceList);

        PriceListDetail priceListDetailFive = new PriceListDetail();
        priceListDetailFive.setHour("14-24");
        priceListDetailFive.setPrice(20D);
        priceListDetailFive.setPriceListId(priceList);

        priceListDetails.add(priceListDetailOne);
        priceListDetails.add(priceListDetailTwo);
        priceListDetails.add(priceListDetailThree);
        priceListDetails.add(priceListDetailFour);
        priceListDetails.add(priceListDetailFive);
        priceList.setPriceListDetails(priceListDetails);

        parkingArea = new ParkingArea();
        parkingArea.setCity("Ankara");
        parkingArea.setCapacity(3);
        parkingArea.setName("Ankara Oto Park");
        parkingArea.setPriceList(priceList);

        checkInOut = new CheckInOut();
        checkInOut.setCheckInDate(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2020-03-31 12:10:10"));
        checkInOut.setParkingArea(parkingArea);

    }

    @Test
    public void testCheckIn_trueStory() throws ParseException {
        //Arrange
        ParkingArea parkingAreaRecorded = parkingAreaDAO.save(parkingArea);
        checkInOut.setParkingArea(parkingAreaRecorded);
        minivan = new Vehicle();
        minivan.setLicensePlate("34 AV 14");
        checkInOut.setVehicle(minivan);

        //Act
        service.checkIn(checkInOut);

        //Verify
        Assert.assertNotNull(minivanDAO.findById(minivan.getLicensePlate()).get());
    }

    @Test
    public void testCheckIn_whenCheckInIsAlreadyExist_shouldThrowRestApiRequestExceptionException() {
        //Act
        parkingAreaDAO.save(parkingArea);
        minivan = new Vehicle();
        minivan.setLicensePlate("34 IST 13");
        checkInOut.setVehicle(minivan);
        minivanDAO.save(checkInOut.getVehicle());
        checkInOutDAO.save(checkInOut);
        //Verify
        Assertions.assertThatThrownBy(() -> service.checkIn(checkInOut)).isInstanceOf(RestApiRequestException.class)
                .hasMessageContaining(ErrorMessages.CHECK_IN_IS_ALREADY_EXIST);
    }

    @Test
    public void testCheckIn_whenNotRecordedParkingArea_shouldThrowRestApiRequestExceptionException() {

        //Act
        minivan = new Vehicle();
        minivan.setLicensePlate("34 AN 12");
        checkInOut.setVehicle(minivan);
        parkingArea.setId(100L);
        //Verify
        Assertions.assertThatThrownBy(() -> service.checkIn(checkInOut)).isInstanceOf(RestApiRequestException.class)
                .hasMessageContaining(ErrorMessages.PARKING_AREA_IS_NOT_FOUND);
    }

    @Test
    public void testCheckIn_whenParkingAreaFullCapacity_shouldThrowRestApiRequestExceptionException() {
        //Arrange
        minivan = new Vehicle();
        minivan.setLicensePlate("34 ASD 11");
        checkInOut.setVehicle(minivan);
        parkingArea.setCapacity(1);
        Vehicle sedan2 = new Vehicle();
        sedan2.setLicensePlate("34 ASD 10");
        //Act
        parkingAreaDAO.save(parkingArea);
        minivanDAO.save(checkInOut.getVehicle());
        checkInOutDAO.save(checkInOut);

        checkInOut.setVehicle(sedan2);

        //Verify
        Assertions.assertThatThrownBy(() -> service.checkIn(checkInOut)).isInstanceOf(RestApiRequestException.class)
                .hasMessageContaining(ErrorMessages.FULL_CAPACITY_IN_PARKING_AREA);
    }

    @Test
    public void testCheckOut_trueStory() throws ParseException {
        //Arrange
        ParkingArea parkingAreaRecorded = parkingAreaDAO.save(parkingArea);
        checkInOut.setParkingArea(parkingAreaRecorded);
        minivan = new Vehicle();
        minivan.setLicensePlate("60 TO 09");
        checkInOut.setVehicle(minivan);
        minivanDAO.save(minivan);
        checkInOutDAO.save(checkInOut);

        //Act
        service.checkOut(minivan);

        //Verify
        Assert.assertNotNull(checkInOutDAO.findCheckInOutsByVehicleLicensePlate(minivan.getLicensePlate()).get(0).getCheckOutDate());
    }

    @Test
    public void testGetParkingDetails_trueStory() throws ParseException {
        //Arrange
        ParkingArea parkingAreaRecorded = parkingAreaDAO.save(parkingArea);
        checkInOut.setParkingArea(parkingAreaRecorded);
        minivan = new Vehicle();
        minivan.setLicensePlate("34 TO 08");
        checkInOut.setVehicle(minivan);
        minivanDAO.save(minivan);
        checkInOutDAO.save(checkInOut);

        //Act & Verify
        Assert.assertNotNull(service.getParkingDetails(minivan.getLicensePlate()));
    }


}
