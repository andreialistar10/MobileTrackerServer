package com.andrei.mobiletracker.device.dao.devicesettings.impl.jpa;

import com.andrei.mobiletracker.device.model.DeviceSettings;
import org.springframework.data.jpa.repository.JpaRepository;

interface DeviceSettingsDaoJpa extends JpaRepository<DeviceSettings, Long> {
}
