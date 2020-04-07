package com.andrei.mobiletracker.device.dao.unregistereddevice.impl.jpa;

import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import org.springframework.data.jpa.repository.JpaRepository;

interface UnregisteredDeviceJpaRepository extends JpaRepository<UnregisteredDevice, String> {
}
