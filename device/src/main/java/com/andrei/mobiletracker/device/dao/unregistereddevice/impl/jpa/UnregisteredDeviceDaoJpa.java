package com.andrei.mobiletracker.device.dao.unregistereddevice.impl.jpa;

import com.andrei.mobiletracker.device.dao.unregistereddevice.UnregisteredDeviceDao;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.model.UnregisteredDeviceState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Override
    public UnregisteredDevice findOneUnregisteredDeviceByIdAndPasswordAndState(String deviceId, String devicePassword, UnregisteredDeviceState state) {

        logger.info("------------------LOGGING  findOneUnregisteredDeviceByIdAndPassword------------------");
        UnregisteredDevice unregisteredDevice = jpaRepository.findByIdAndPasswordAndState(deviceId,devicePassword, state);
        logger.info("-----------------SUCCESSFUL findOneUnregisteredDeviceByIdAndPassword-----------------");
        return unregisteredDevice;
    }

    @Override
    public UnregisteredDevice updateOneUnregisteredDevice(UnregisteredDevice foundUnregisteredDevice) {

        logger.info("------------------LOGGING  updateOneUnregisteredDevice------------------");
        UnregisteredDevice unregisteredDevice = jpaRepository.save(foundUnregisteredDevice);
        logger.info("-----------------SUCCESSFUL updateOneUnregisteredDevice-----------------");
        return unregisteredDevice;
    }

    @Override
    public UnregisteredDevice findOneUnregisteredDeviceById(String id) {

        logger.info("------------------LOGGING  findOneUnregisteredDeviceById------------------");
        Optional<UnregisteredDevice> unregisteredDeviceOptional = jpaRepository.findById(id);
        logger.info("-----------------SUCCESSFUL findOneUnregisteredDeviceById-----------------");
        return unregisteredDeviceOptional.orElse(null);
    }

    @Override
    public void deleteOneUnregisteredDevice(String id) {

        logger.info("------------------LOGGING  deleteOneUnregisteredDevice------------------");
        jpaRepository.deleteById(id);
        logger.info("-----------------SUCCESSFUL deleteOneUnregisteredDevice-----------------");
    }
}
