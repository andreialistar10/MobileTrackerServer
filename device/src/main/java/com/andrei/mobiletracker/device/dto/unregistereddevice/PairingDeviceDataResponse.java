package com.andrei.mobiletracker.device.dto.unregistereddevice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PairingDeviceDataResponse {

    private String ownerUsername;
    private String tokenApi;
    private String refreshToken;
    private String deviceId;
    private Integer timeInterval;
    private String name;
}
