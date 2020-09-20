package com.andrei.mobiletracker.device.dto.deviceconnectivity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PendingPairingData {

    private String username;
}
