package com.andrei.mobiletracker.location.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOCATIONS")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long code;

    @Column(name = "DEVICE_CODE", nullable = false)
    private String deviceCode;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "SENT_AT", nullable = false)
    private Long date;

    @Column(name = "LOCATION_NAME")
    private String address;
}
