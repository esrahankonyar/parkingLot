package com.huawei.parkinglot;


import com.huawei.parkinglot.service.MinivanVehicleServiceTest;
import com.huawei.parkinglot.service.ParkingAreaServiceImplTest;
import com.huawei.parkinglot.service.SedanVehicleServiceTest;
import com.huawei.parkinglot.service.SuvVehicleServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ParkingAreaServiceImplTest.class,
        SedanVehicleServiceTest.class,
        SuvVehicleServiceTest.class,
        MinivanVehicleServiceTest.class,
})
public class SuiteTest {
}
