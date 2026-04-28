package com.springboot.simplyfly.model;

import com.springboot.simplyfly.enums.SeatClass;
import com.springboot.simplyfly.enums.SeatStatus;
import com.springboot.simplyfly.enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "row_num")
    private int rowNumber;
    @Column(name = "column_name")
    private String columnName;

    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;

    @ManyToOne
    @JoinColumn(name = "route_flight_id")
    private RouteFlight routeFlight;
}