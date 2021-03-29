package com.huawei.parkinglot.entity;

import com.huawei.parkinglot.entity.vehicle.Vehicle;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "check_in_out")
@Data
public class CheckInOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "check_in_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date checkInDate;

    @Column(name = "check_out_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date checkOutDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "parking_area_id")
    private ParkingArea parkingArea;

    @Column(name = "fee")
    private Double fee;

}
