package com.andrei.mobiletracker.device.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UNREGISTERED_DEVICES")
public class UnregisteredDevice {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", nullable = false)
    private UnregisteredDeviceState state;

    @Column(name = "PAIRING_PASSWORD")
    private String password;

    @Column(name = "USER_TRYING_TO_PAIR")
    private String tryingToPairingUsername;

    @Column(name = "ID_AFTER_PAIRING")
    private String idAfterPairing;
}
