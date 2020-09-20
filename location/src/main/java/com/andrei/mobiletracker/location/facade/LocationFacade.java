package com.andrei.mobiletracker.location.facade;

import com.andrei.mobiletracker.location.dto.location.LocationData;
import com.andrei.mobiletracker.location.dto.location.collection.FilteredLocationsData;
import com.andrei.mobiletracker.location.dto.location.collection.LatestLocationsData;

import java.util.List;

public interface LocationFacade {

    void saveLocations(List<LocationData> locationDataList, String deviceCode);

    LatestLocationsData findLatestLocations();

    FilteredLocationsData findAllLocationsByPeriodTimeAndDeviceCode(String deviceCode, Long startDate, Long endDate, String username);
}
