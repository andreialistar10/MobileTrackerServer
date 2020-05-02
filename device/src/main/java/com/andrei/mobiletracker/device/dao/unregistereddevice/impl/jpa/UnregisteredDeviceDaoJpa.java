package com.andrei.mobiletracker.device.dao.unregistereddevice.impl.jpa;

import com.andrei.mobiletracker.device.dao.unregistereddevice.UnregisteredDeviceDao;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories(basePackageClasses = UnregisteredDeviceJpaRepository.class)
public class UnregisteredDeviceDaoJpa implements UnregisteredDeviceDao {

    @Autowired
    private UnregisteredDeviceJpaRepository jpaRepository;

    private static final Logger logger = LogManager.getLogger(UnregisteredDeviceDaoJpa.class);

    @Override
    public UnregisteredDevice saveOneUnregisteredDevice(UnregisteredDevice unregisteredDevice) {

        logger.info("------------------LOGGING  saveOneUnregisteredDevice------------------");
        unregisteredDevice = jpaRepository.save(unregisteredDevice);
        logger.info("-----------------SUCCESSFUL saveOneUnregisteredDevice-----------------");
        return unregisteredDevice;
    }
}
