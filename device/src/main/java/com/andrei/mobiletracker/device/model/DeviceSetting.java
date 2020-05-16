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
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TIME_INTERVAL", nullable = false)
    private Integer timeInterval;

    @Column(name = "NAME", nullable = false)
    private String name;
}
