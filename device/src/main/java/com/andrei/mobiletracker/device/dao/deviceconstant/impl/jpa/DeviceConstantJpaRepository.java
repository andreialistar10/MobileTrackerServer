package com.andrei.mobiletracker.device.dao.deviceconstant.impl.jpa;

import com.andrei.mobiletracker.device.model.DeviceConstant;
import com.andrei.mobiletracker.device.model.DeviceConstantName;
import org.springframework.data.jpa.repository.JpaRepository;

interface DeviceConstantJpaRepository extends JpaRepository<DeviceConstant, DeviceConstantName> {
}
