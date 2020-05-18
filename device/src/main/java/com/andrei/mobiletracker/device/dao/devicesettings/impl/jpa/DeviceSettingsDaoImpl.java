package com.andrei.mobiletracker.device.dao.devicesettings.impl.jpa;

import com.andrei.mobiletracker.device.dao.devicesettings.DeviceSettingsDao;
import com.andrei.mobiletracker.device.model.DeviceSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories(basePackageClasses = DeviceSettingsDaoImpl.class)
public class DeviceSettingsDaoImpl implements DeviceSettingsDao {

    private static final Logger logger = LogManager.getLogger(DeviceSettingsDaoImpl.class);

    @Autowired
    private DeviceSettingsDaoJpa deviceSettingsDaoJpa;

    @Override
    public DeviceSettings saveOneDeviceSettings(DeviceSettings deviceSettings) {

        logger.info("------------------LOGGING  saveOneDeviceSettings------------------");
        DeviceSettings savedDeviceSettings = deviceSettingsDaoJpa.save(deviceSettings);
        logger.info("-----------------SUCCESSFUL saveOneDeviceSettings-----------------");
        return savedDeviceSettings;
    }
}
