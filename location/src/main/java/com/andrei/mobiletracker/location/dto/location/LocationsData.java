package com.andrei.mobiletracker.location.dto.location;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LocationsData {

    private List<LocationData> locations;
}
