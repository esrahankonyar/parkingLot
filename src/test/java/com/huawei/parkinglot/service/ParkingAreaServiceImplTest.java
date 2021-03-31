package com.huawei.parkinglot.service;

import com.huawei.parkinglot.ParkinglotApplication;
import com.huawei.parkinglot.constants.ErrorMessages;
import com.huawei.parkinglot.dao.ParkingAreaDAO;
import com.huawei.parkinglot.entity.ParkingArea;
import com.huawei.parkinglot.entity.PriceList;
import com.huawei.parkinglot.entity.PriceListDetail;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParkinglotApplication.class})
public class ParkingAreaServiceImplTest {

    @InjectMocks
    private ParkingAreaServiceImpl service;

    @Autowired
    private ParkingAreaDAO parkingAreaDAO;

    private ParkingArea parkingArea;


    @Before
    public void setup() {
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


    }

    @Test
    public void testCreateParkingArea_trueStory() {
        //Act
        service.createParkingArea(parkingArea);

        //Verify
        Assert.assertNotNull(parkingAreaDAO.findById(1L).get());
    }

    @Test
    public void testCreateParkingArea_whenMissingPriceListHours_shouldThrowRestApiRequestExceptionException() {
        //Act
        parkingArea.getPriceList().getPriceListDetails().get(4).setHour("14-25");

        //Verify
        Assertions.assertThatThrownBy(() -> service.createParkingArea(parkingArea)).isInstanceOf(RestApiRequestException.class)
                .hasMessageContaining(ErrorMessages.NOT_SUITABLE_HOURS);
    }

    @Test
    public void testUpdateParkingArea_trueStory() {
        //Arrange & Act
        ParkingArea parkingAreaRecorded = parkingAreaDAO.save(parkingArea);
        parkingArea.setCity("İstanbul");
        service.updateParkingArea(parkingArea);
        String expectedValue = "İstanbul";

        //Verify
        Assert.assertEquals(expectedValue, parkingAreaDAO.findById(parkingAreaRecorded.getId()).get().getCity());
    }

    @Test
    public void testDeleteParkingArea_trueStory() {
        //Act
        ParkingArea parkingAreaRecord = parkingAreaDAO.save(parkingArea);
        service.deleteParkingArea(parkingAreaRecord.getId());

        //Verify
        Assert.assertTrue(!parkingAreaDAO.findById(parkingAreaRecord.getId()).isPresent());
    }

    @Test
    public void testGetParkingAreaByName_trueStory() {
        //Act
        ParkingArea parkingAreaRecord = parkingAreaDAO.save(parkingArea);
        List<ParkingArea> parkingAreas = service.getParkingAreaByName(parkingAreaRecord.getName());

        //Verify
        Assert.assertTrue(!parkingAreas.isEmpty());
    }

}
