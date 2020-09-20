package com.andrei.mobiletracker.location.facade.impl;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.location.dto.device.DeviceInformation;
import com.andrei.mobiletracker.location.dto.device.DevicesInformation;
import com.andrei.mobiletracker.location.dto.location.FilteredLocationData;
import com.andrei.mobiletracker.location.dto.location.LatestLocationData;
import com.andrei.mobiletracker.location.dto.location.LocationData;
import com.andrei.mobiletracker.location.dto.location.collection.FilteredLocationsData;
import com.andrei.mobiletracker.location.dto.location.collection.LatestLocationsData;
import com.andrei.mobiletracker.location.facade.LocationFacade;
import com.andrei.mobiletracker.location.model.Location;
import com.andrei.mobiletracker.location.service.GeoCoderService;
import com.andrei.mobiletracker.location.service.LocationService;
import com.andrei.mobiletracker.location.util.communication.MicroservicesCaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private Converter<Location, LatestLocationData> latestLocationDataFromLocationConverter;

    @Autowired
    private LocationService locationService;

    @Autowired
    @Qualifier(value = "locationiqGeocoderService")
    private GeoCoderService geoCoderService;

    @Override
    public void saveLocations(List<LocationData> locationDataList, String deviceCode) {

        logger.info("------------------LOGGING  saveLocation------------------");
        locationDataList.forEach(locationData -> {
            Location location = locationFromLocationDataConverter.convert(locationData);
            location.setDeviceCode(deviceCode);
            String address = geoCoderService.getAddressByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
            location.setAddress(address);
            locationService.saveOneLocation(location);
        });
        logger.info("-----------------SUCCESSFUL saveLocation-----------------");
    }

    @Override
    public LatestLocationsData findLatestLocations() {

        logger.info("------------------LOGGING  findLatestLocations------------------");
        DevicesInformation devices = microservicesCaller.findAllDevicesForCurrentUser();
        List<String> deviceIds = getDeviceIds(devices.getDevices());
        List<Location> locations = locationService.findLatestLocations(deviceIds);
        List<LatestLocationData> latestLocationDataList = convertLocationListToLatestLocationDataList(locations, devices.getDevices());
        logger.info("-----------------SUCCESSFUL findLatestLocations-----------------");
        return LatestLocationsData.builder()
                .locations(latestLocationDataList)
                .build();
    }

    private List<LatestLocationData> convertLocationListToLatestLocationDataList(List<Location> locations, List<DeviceInformation> devices) {

        Map<String, DeviceInformation> devicesMap = generateDevicesMap(devices);
        return locations.stream()
                .map(latestLocationDataFromLocationConverter::convert)
                .peek(latestLocationData -> {
                    DeviceInformation deviceInformation = devicesMap.get(latestLocationData.getDeviceCode());
                    latestLocationData.setDeviceName(deviceInformation.getName());
                })
                .collect(Collectors.toList());
    }

    private Map<String, DeviceInformation> generateDevicesMap(List<DeviceInformation> devices) {

        return devices.stream()
                .collect(Collectors.toMap(DeviceInformation::getId, Function.identity()));
    }

    private List<String> getDeviceIds(List<DeviceInformation> devices) {

        return devices.stream()
                .map(DeviceInformation::getId)
                .collect(Collectors.toList());
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
