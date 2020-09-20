package com.andrei.mobiletracker.location.dao.impl.jpa;

import com.andrei.mobiletracker.location.dao.LocationDao;
import com.andrei.mobiletracker.location.model.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories(basePackageClasses = {LocationDaoJpa.class})
public class LocationDaoImpl implements LocationDao {

    private static final Logger logger = LogManager.getLogger(LocationDaoImpl.class);

    @Autowired
    private LocationDaoJpa locationDaoJpa;

    @Override
    public Location saveOneLocation(Location location) {

        logger.info("------------------LOGGING  saveOneLocation------------------");
        location = locationDaoJpa.save(location);
        logger.info("-----------------SUCCESSFUL saveOneLocation-----------------");
        return location;
    }

    @Override
    public List<Location> findLatestLocations(List<String> deviceIdList) {

        logger.info("------------------LOGGING  saveOneLocation------------------");
        List<Location> locations = locationDaoJpa.findLatestLocations(deviceIdList);
        logger.info("-----------------SUCCESSFUL saveOneLocation-----------------");
        return locations;
    }

    @Override
    public List<Location> findAllLocationsByPeriodTimeAndDeviceCode(Long startDate, Long endDate, String deviceCode) {

        logger.info("------------------LOGGING  saveOneLocation------------------");
        List<Location> locations = locationDaoJpa.findAllByDateBetweenAndDeviceCodeOrderByDateDesc(startDate,endDate,deviceCode);
        logger.info("-----------------SUCCESSFUL saveOneLocation-----------------");
        return locations;
    }
}
