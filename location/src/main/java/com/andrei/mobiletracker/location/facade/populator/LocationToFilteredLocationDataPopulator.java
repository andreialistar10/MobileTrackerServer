package com.andrei.mobiletracker.location.facade.populator;

import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.location.dto.location.FilteredLocationData;
import com.andrei.mobiletracker.location.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationToFilteredLocationDataPopulator implements Populator<Location, FilteredLocationData> {

    @Override
    public void populate(FilteredLocationData filteredLocationData, Location location) {

        filteredLocationData.setCode(location.getCode());
        filteredLocationData.setLatitude(location.getLatitude());
        filteredLocationData.setLongitude(location.getLongitude());
        filteredLocationData.setDate(location.getDate());
        filteredLocationData.setAddress(location.getAddress());
    }
}
