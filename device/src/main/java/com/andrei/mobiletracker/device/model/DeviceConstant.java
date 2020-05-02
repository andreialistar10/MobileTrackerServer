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
@Table(name = "CONSTANTS")
public class DeviceConstant {

    @Id
    @Column(name = "CONSTANT_NAME")
    @Enumerated(EnumType.STRING)
    private DeviceConstantName name;

    @Column(name = "CONSTANT_VALUE", nullable = false)
    private Long value;
}
