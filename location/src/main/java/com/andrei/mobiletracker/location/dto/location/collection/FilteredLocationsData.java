package com.andrei.mobiletracker.location.dto.location.collection;

import com.andrei.mobiletracker.location.dto.location.FilteredLocationData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilteredLocationsData {

    private List<FilteredLocationData> locations;
}
