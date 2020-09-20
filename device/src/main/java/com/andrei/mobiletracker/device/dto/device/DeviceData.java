package com.andrei.mobiletracker.device.dto.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceData {

    private String id;
    private String name;
    private Long date;
}
