package com.andrei.mobiletracker.location.service.impl;

import com.andrei.mobiletracker.location.dao.LocationDao;
import com.andrei.mobiletracker.location.model.Location;
import com.andrei.mobiletracker.location.service.LocationService;
import com.andrei.mobiletracker.location.service.validator.ExistingDeviceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger logger = LogManager.getLogger(LocationServiceImpl.class);

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private ExistingDeviceValidator existingDeviceValidator;

    @Transactional
    @Override
    public Location saveOneLocation(Location location) {

        logger.info("------------------LOGGING  saveOneLocation------------------");
        long currentTimestamp = System.currentTimeMillis();
        if (location.getDate() == null) {
            location.setDate(currentTimestamp);
        }
        location = locationDao.saveOneLocation(location);
        logger.info("-----------------SUCCESSFUL saveOneLocation-----------------");
        return location;
    }

    @Transactional
    @Override
    public List<Location> findLatestLocations(List<String> deviceIdList) {

        logger.info("------------------LOGGING  findLatestLocations------------------");
        List<Location> locations = locationDao.findLatestLocations(deviceIdList);
        logger.info("-----------------SUCCESSFUL findLatestLocations-----------------");
        return locations;
    }

    @Override
    public List<Location> findAllLocationsByPeriodTimeAndDeviceCode(Long startDate, Long endDate, String deviceCode) {

        logger.info("------------------LOGGING  findAllLocationsByPeriodTimeAndDeviceCode------------------");
        existingDeviceValidator.validate(deviceCode);
        List<Location> locations = locationDao.findAllLocationsByPeriodTimeAndDeviceCode(startDate, endDate, deviceCode);
        logger.info("-----------------SUCCESSFUL findAllLocationsByPeriodTimeAndDeviceCode-----------------");
        return locations;
    }
}
