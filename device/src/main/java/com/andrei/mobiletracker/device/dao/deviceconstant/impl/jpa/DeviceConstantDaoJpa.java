package com.andrei.mobiletracker.device.dao.deviceconstant.impl.jpa;

import com.andrei.mobiletracker.device.controller.DeviceController;
import com.andrei.mobiletracker.device.dao.deviceconstant.DeviceConstantDao;
import com.andrei.mobiletracker.device.model.DeviceConstant;
import com.andrei.mobiletracker.device.model.DeviceConstantName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories(basePackageClasses = DeviceConstantJpaRepository.class)
public class DeviceConstantDaoJpa implements DeviceConstantDao {

    @Autowired
    private DeviceConstantJpaRepository jpaRepository;

    private static final Logger logger = LogManager.getLogger(DeviceConstantDaoJpa.class);

    @Override
    public DeviceConstant findOneByDeviceConstantName(DeviceConstantName deviceConstantName) {

        logger.info("------------------LOGGING  findOneByDeviceConstantName------------------");
        logger.info("Device constant name: {}", deviceConstantName);
        DeviceConstant deviceConstant = jpaRepository.findById(deviceConstantName).orElse(null);
        loggingDeviceConstant(deviceConstant, deviceConstantName);
        logger.info("-----------------SUCCESSFUL findOneByDeviceConstantName-----------------");
        return deviceConstant;
    }

    @Override
    public DeviceConstant updateOneDeviceConstant(DeviceConstant deviceConstant) {

        logger.info("------------------LOGGING  updateOneDeviceConstant------------------");
        deviceConstant = jpaRepository.save(deviceConstant);
        logger.info("-----------------SUCCESSFUL updateOneDeviceConstant-----------------");
        return deviceConstant;
    }

    private void loggingDeviceConstant(DeviceConstant deviceConstant, DeviceConstantName deviceConstantName) {

        if (deviceConstant == null){
            logger.info("Does not exists a device constant with name {}!", deviceConstantName);
        }
        else {
            logger.info("Value for device constant name {} is: {}",deviceConstantName,deviceConstant.getValue());
        }
    }
}
