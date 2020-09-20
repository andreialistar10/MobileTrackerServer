package com.andrei.mobiletracker.location.facade.converter;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.location.dto.location.LocationData;
import com.andrei.mobiletracker.location.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationFromLocationDataConverter implements Converter<LocationData, Location> {

    @Autowired
    private Populator<LocationData, Location> locationDataToLocationPopulator;

    @Override
    public Location convert(LocationData locationData) {

        Location location = new Location();
        locationDataToLocationPopulator.populate(location, locationData);
        return location;
    }
}
