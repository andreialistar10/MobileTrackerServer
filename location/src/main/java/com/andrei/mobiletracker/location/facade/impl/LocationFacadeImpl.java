package com.andrei.mobiletracker.location.facade.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.location.dto.device.DevicesInformation;
import com.andrei.mobiletracker.location.dto.location.FilteredLocationData;
import com.andrei.mobiletracker.location.dto.location.LocationData;
import com.andrei.mobiletracker.location.dto.location.LocationsData;
import com.andrei.mobiletracker.location.dto.location.collection.FilteredLocationsData;
import com.andrei.mobiletracker.location.dto.location.collection.LatestLocationsData;
import com.andrei.mobiletracker.location.facade.LocationFacade;
import com.andrei.mobiletracker.location.model.Location;
import com.andrei.mobiletracker.location.service.LocationService;
import com.andrei.mobiletracker.location.util.communication.MicroservicesCaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationFacadeImpl implements LocationFacade {

    private static final Logger logger = LogManager.getLogger(LocationFacadeImpl.class);

    @Autowired
    private MicroservicesCaller microservicesCaller;

    @Autowired
    private Converter<LocationData, Location> locationFromLocationDataConverter;

    @Autowired
    private Converter<Location, FilteredLocationData> filteredLocationDataFromLocationConverter;

    @Autowired
    private LocationService locationService;

    @Override
    public void saveLocations(LocationsData locationsData, String deviceCode) {

        logger.info("------------------LOGGING  saveLocation------------------");
        locationsData.getLocations().forEach(locationData -> {
            Location location = locationFromLocationDataConverter.convert(locationData);
            location.setDeviceCode(deviceCode);
            locationService.saveOneLocation(location);
        });
        logger.info("-----------------SUCCESSFUL saveLocation-----------------");
    }

    @Override
    public LatestLocationsData findLatestLocations() {

        logger.info("------------------LOGGING  findLatestLocations------------------");
        DevicesInformation devices = microservicesCaller.findAllDevicesForCurrentUser();
        List<Location> locations = locationService.findLatestLocations(devices.getDevices());
        logger.info("-----------------SUCCESSFUL findLatestLocations-----------------");
        return LatestLocationsData.builder()
                .locations(locations)
                .build();
    }

    @Override
    public FilteredLocationsData findAllLocationsByPeriodTimeAndDeviceCode(String deviceCode, Long startDate, Long endDate, String username) {

        logger.info("------------------LOGGING  findAllLocationsByPeriodTimeAndDeviceCode------------------");
        List<Location> locations = locationService.findAllLocationsByPeriodTimeAndDeviceCode(startDate, endDate, deviceCode);
        List<FilteredLocationData> filteredLocationDataList = filteredLocationDataFromLocationConverter.convertAll(locations);
        logger.info("-----------------SUCCESSFUL findAllLocationsByPeriodTimeAndDeviceCode-----------------");
        return FilteredLocationsData.builder()
                .locations(filteredLocationDataList)
                .build();
    }
}
