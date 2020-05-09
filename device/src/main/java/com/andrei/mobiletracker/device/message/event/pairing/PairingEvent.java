package com.andrei.mobiletracker.device.message.event.pairing;

import com.andrei.mobiletracker.device.message.event.MobileTrackerMessageEvent;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PairingEvent implements MobileTrackerMessageEvent {

    private UnregisteredDeviceState state;
    private String ownerUsername;
    private String deviceCode;
}
