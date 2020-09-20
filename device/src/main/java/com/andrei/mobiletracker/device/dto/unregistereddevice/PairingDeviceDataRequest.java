package com.andrei.mobiletracker.device.dto.unregistereddevice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PairingDeviceDataRequest {

    @NotBlank
    private String ownerUsername;
}
