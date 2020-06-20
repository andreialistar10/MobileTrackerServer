package com.andrei.mobiletracker.location.facade.converter;

import com.andrei.mobiletracker.beans.converter.Converter;
import com.andrei.mobiletracker.beans.populator.Populator;
import com.andrei.mobiletracker.location.dto.location.LatestLocationData;
import com.andrei.mobiletracker.location.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LatestLocationDataFromLocationConverter implements Converter<Location, LatestLocationData> {

    @Autowired
    private Populator<Location, LatestLocationData> locationToLatestLocationDataPopulator;

    @Override
    public LatestLocationData convert(Location location) {

        LatestLocationData latestLocationData = new LatestLocationData();
        locationToLatestLocationDataPopulator.populate(latestLocationData, location);
        return latestLocationData;
    }
}
