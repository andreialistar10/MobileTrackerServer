package com.andrei.mobiletracker.location.facade.converter;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.location.dto.location.FilteredLocationData;
import com.andrei.mobiletracker.location.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilteredLocationDataFromLocation implements Converter<Location, FilteredLocationData> {

    @Autowired
    private Populator<Location, FilteredLocationData> locationToFilteredLocationDataPopulator;

    @Override
    public FilteredLocationData convert(Location location) {

        FilteredLocationData filteredLocationData = new FilteredLocationData();
        locationToFilteredLocationDataPopulator.populate(filteredLocationData, location);
        return filteredLocationData;
    }
}
