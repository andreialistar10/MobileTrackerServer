package com.andrei.mobiletracker.device.dto.device;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceData {

    private String id;
    private String name;
    private Long registeredOn;
}
