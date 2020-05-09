package com.andrei.mobiletracker.device.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "STATE")
    private UnregisteredDeviceState state;

    @Column(name = "PAIRING_PASSWORD")
    private String password;

    @Column(name = "USER_TRYING_TO_PAIR")
    private String tryingToPairingUsername;
}
