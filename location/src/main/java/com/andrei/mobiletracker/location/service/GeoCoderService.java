package com.andrei.mobiletracker.location.service;

public interface GeoCoderService {

    String getAddressByLatitudeAndLongitude(Double latitude, Double longitude);
}
