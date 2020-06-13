package com.andrei.mobiletracker.location.dto.location.collection;

import com.andrei.mobiletracker.location.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LatestLocationsData {

    private List<Location> locations;
}
