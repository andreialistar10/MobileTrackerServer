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
@Table(name = "DEVICES")
public class Device {

    @Id
    @Column(name = "DEVICE_CODE")
    private String code;

    @Column(name = "DELETED")
    private Boolean deleted;

    @Column(name = "OWNER_USERNAME", nullable = false)
    private String ownerUsername;

    @Column(name = "REGISTERED_ON", nullable = false)
    private Long registeredOn;

    @Column(name = "TOKEN_API", nullable = false)
    private String tokenApi;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, targetEntity = DeviceSettings.class)
    @JoinColumn(name = "DEVICE_SETTINGS_ID", referencedColumnName = "ID", nullable = false)
    private DeviceSettings deviceSettings;
}
