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
public class Devices {

    @Id
    @Column(name = "DEVICE_CODE")
    private String code;

    @Column(name = "DELETED")
    private Boolean deleted;

    @Column(name = "OWNER_USERNAME", nullable = false)
    private String ownerUsername;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, targetEntity = DeviceSetting.class)
    @JoinColumn(name = "DEVICE_SETTINGS_ID", referencedColumnName = "DEVICE_SETTINGS_ID", nullable = false)
    private DeviceSetting deviceSetting;
}
