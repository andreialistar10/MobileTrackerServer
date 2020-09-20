package com.andrei.mobiletracker.device.message.event.pairing;

import com.andrei.mobiletracker.device.message.event.MobileTrackerMessageEvent;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PairingEvent implements MobileTrackerMessageEvent {

    private UnregisteredDeviceState state;
    private String ownerUsername;
    private String deviceCode;
    private String deviceName;
    private Long registeredOn;
    private String deviceCodeAfterPairing;
}
