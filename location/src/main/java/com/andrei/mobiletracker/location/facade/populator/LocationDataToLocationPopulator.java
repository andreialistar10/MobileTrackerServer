package com.andrei.mobiletracker.location.facade.populator;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.location.dto.location.LocationData;
import com.andrei.mobiletracker.location.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationDataToLocationPopulator implements Populator<LocationData, Location> {

    @Override
    public void populate(Location location, LocationData locationData) {
        location.setDate(locationData.getTimestamp());
        location.setLatitude(locationData.getLatitude());
        location.setLongitude(locationData.getLongitude());
    }
}
