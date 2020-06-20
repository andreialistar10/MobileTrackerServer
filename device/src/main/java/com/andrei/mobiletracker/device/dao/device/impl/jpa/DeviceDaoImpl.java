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
        Device device = jpaRepository.findByCodeAndOwnerUsername(id, ownerUsername);
        logger.info("-----------------SUCCESSFUL findOneDeviceByDeviceIdAndOwnerUsername-----------------");
        return device;
    }

    @Override
    public List<Device> findAllAvailableDevicesByOwnerUsername(String username) {

        logger.info("------------------LOGGING  findAllDevicesByOwnerUsername------------------");
        List<Device> devices = jpaRepository.findAllByOwnerUsernameAndDeleted(username, false);
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
