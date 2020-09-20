package com.andrei.mobiletracker.device.dto.deviceconnectivity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnregisteredDeviceCredentialsData {

    private String id;
    private String password;
    private String tryingToPairUsername;
    private String idAfterPairing;
}
