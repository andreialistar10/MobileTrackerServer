package com.andrei.mobiletracker.location.dto.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LatestLocationData {

    private Long id;
    private String deviceCode;
    private Double latitude;
    private Double longitude;
    private Long date;
    private String address;
    private String deviceName;
}
