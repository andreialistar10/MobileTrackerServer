package com.andrei.mobiletracker.location.facade.populator;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.location.dto.location.LatestLocationData;
import com.andrei.mobiletracker.location.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationToLatestLocationDataPopulator implements Populator<Location, LatestLocationData> {

    @Override
    public void populate(LatestLocationData latestLocationData, Location location) {

        latestLocationData.setId(location.getCode());
        latestLocationData.setAddress(location.getAddress());
        latestLocationData.setDate(location.getDate());
        latestLocationData.setDeviceCode(location.getDeviceCode());
        latestLocationData.setLatitude(location.getLatitude());
        latestLocationData.setLongitude(location.getLongitude());
    }
}
