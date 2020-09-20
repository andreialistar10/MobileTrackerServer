package com.andrei.mobiletracker.device.dto.unregistereddevice;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnregisteredDeviceDataResponse {

    private String id;
    private String name;
    private String token;
}
