package com.andrei.mobiletracker.location.service.impl;

import com.andrei.mobiletracker.location.service.GeoCoderService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component(value = "locationiqGeocoderService")
public class LocationiqGeoCoderService implements GeoCoderService {

    private static final String PROVIDER_URL = "https://eu1.locationiq.com/v1/reverse.php?key=pk.d6fad39966cb6dfbaeba4de849417e5a&lat=";

    private static final int MIN_TIME_BETWEEN_CALLS = 500;

    private RestTemplate restTemplate = new RestTemplate();

    private long lastCall = 0L;

    @Override
    public synchronized String getAddressByLatitudeAndLongitude(Double latitude, Double longitude) {

        String address;
        try {
            long currentTime = System.currentTimeMillis();
            long diff = currentTime - lastCall;
            if (diff < MIN_TIME_BETWEEN_CALLS) {
                Thread.sleep(MIN_TIME_BETWEEN_CALLS - diff);
            }
            StringBuilder stringBuilder = new StringBuilder(PROVIDER_URL);
            stringBuilder.append(latitude)
                    .append("&lon=")
                    .append(longitude)
                    .append("&format=json");
            ResponseEntity<LocationiqResponse> response = restTemplate.getForEntity(stringBuilder.toString(), LocationiqResponse.class);
            lastCall = System.currentTimeMillis();
            address = Objects.requireNonNull(response.getBody()).getDisplayName();
        } catch (Exception e) {
            address = "";
            e.printStackTrace();
        }
        return address;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class LocationiqResponse{

        @JsonProperty("display_name")
        private String displayName;
    }
}
