package com.andrei.mobiletracker.location.dto.location;

import com.andrei.mobiletracker.location.beans.annotation.constraint.LocationTimestampConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationData {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @LocationTimestampConstraint
    private Long timestamp;
}
