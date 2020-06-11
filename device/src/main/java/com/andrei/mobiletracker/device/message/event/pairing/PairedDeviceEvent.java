package com.andrei.mobiletracker.device.message.event.pairing;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PairedDeviceEvent extends PairingEvent {

    private String deviceName;
    private Long registeredOn;
}
