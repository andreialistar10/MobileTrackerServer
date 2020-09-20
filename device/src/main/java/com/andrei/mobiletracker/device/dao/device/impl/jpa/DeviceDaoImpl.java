package com.andrei.mobiletracker.device.dao.device.impl.jpa;

import com.andrei.mobiletracker.device.dao.device.DeviceDao;
import com.andrei.mobiletracker.device.model.Device;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories(basePackageClasses = DeviceDaoJpa.class)
public class DeviceDaoImpl implements DeviceDao {

    @Autowired
    private DeviceDaoJpa jpaRepository;

    private static final Logger logger = LogManager.getLogger(DeviceDaoImpl.class);

    @Override
    public Device findOneDeviceByDeviceIdAndOwnerUsername(String id, String ownerUsername) {

        logger.info("------------------LOGGING  findOneDeviceByDeviceIdAndOwnerUsername------------------");
        Device device = jpaRepository.findByCodeAndOwnerUsernameAndDeleted(id, ownerUsername, false);
        logger.info("-----------------SUCCESSFUL findOneDeviceByDeviceIdAndOwnerUsername-----------------");
        return device;
    }

    @Override
    public Device findOneDeviceByDeviceIdAndOwnerUsername(String id, String ownerUsername, boolean allowDeleted) {

        logger.info("------------------LOGGING  findOneDeviceByDeviceIdAndOwnerUsername------------------");
        Device device;
        if (allowDeleted) {
            device = jpaRepository.findByCodeAndOwnerUsername(id, ownerUsername);
        } else{
            device = jpaRepository.findByCodeAndOwnerUsernameAndDeleted(id, ownerUsername, false);
        }
        logger.info("-----------------SUCCESSFUL findOneDeviceByDeviceIdAndOwnerUsername-----------------");
        return device;
    }

    @Override
    public List<Device> findAllDevicesByOwnerUsernameAndDeleted(String username, boolean deleted) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices = jpaRepository.findAllByOwnerUsernameAndDeleted(username, deleted);
        logger.info("-----------------SUCCESSFUL findAllDevicesByOwnerUsername-----------------");
        return devices;
    }

    @Override
    public List<Device> findAllDevicesByOwnerUsername(String username) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices = jpaRepository.findAllByOwnerUsername(username);
        logger.info("-----------------SUCCESSFUL findAllDevicesByOwnerUsername-----------------");
        return devices;
    }

    @Override
    public Device saveOrUpdateOneDevice(Device device) {

        logger.info("------------------LOGGING  saveOrUpdateOneDevice------------------");
        Device savedDevice = jpaRepository.save(device);
        logger.info("-----------------SUCCESSFUL saveOrUpdateOneDevice-----------------");
        return savedDevice;
    }
}
