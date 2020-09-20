package com.andrei.mobiletracker.device.dto.unregistereddevice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnregisteredDevicePasswordData {

    private String password;
}
