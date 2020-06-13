package com.andrei.mobiletracker.location.dao;

import com.andrei.mobiletracker.location.model.Location;

import java.util.List;

public interface LocationDao {

    Location saveOneLocation(Location location);

    List<Location> findLatestLocations(List<String> deviceIdList);

    List<Location> findAllLocationsByPeriodTimeAndDeviceCode(Long startDate, Long endDate, String deviceCode);
}
