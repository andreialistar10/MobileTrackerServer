package com.andrei.mobiletracker.location.facade;

import com.andrei.mobiletracker.location.dto.location.LocationsData;
import com.andrei.mobiletracker.location.dto.location.collection.FilteredLocationsData;
import com.andrei.mobiletracker.location.dto.location.collection.LatestLocationsData;

public interface LocationFacade {

    void saveLocations(LocationsData locationData, String deviceCode);

    LatestLocationsData findLatestLocations();

    FilteredLocationsData findAllLocationsByPeriodTimeAndDeviceCode(String deviceCode, Long startDate, Long endDate, String username);
}
