package com.andrei.mobiletracker.device.model;

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
@Table(name = "DEVICE_SETTINGS")
public class DeviceSetting {

    @Id
    @Column(name = "DEVICE_SETTINGS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TIME_INTERVAL")
    private Integer timeInterval;

    @Column(name = "GEOFENCE_RADIUS")
    private Double geofenceRadius;

    @Column(name = "GEOFENCE_CENTER_LATITUDE")
    private Double geofenceCenterLatitude;

    @Column(name = "GEOFENCE_CENTER_LONGITUDE")
    private Double geofenceCenterLongitude;
}
