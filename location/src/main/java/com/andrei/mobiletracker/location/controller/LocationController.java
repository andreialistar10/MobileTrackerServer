package com.andrei.mobiletracker.location.controller;

import com.andrei.mobiletracker.location.dto.location.LocationData;
import com.andrei.mobiletracker.location.dto.location.collection.FilteredLocationsData;
import com.andrei.mobiletracker.location.dto.location.collection.LatestLocationsData;
import com.andrei.mobiletracker.location.facade.LocationFacade;
import com.andrei.mobiletracker.location.service.exception.LocationExceptionType;
import com.andrei.mobiletracker.location.service.exception.LocationServiceException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private static final Logger logger = LogManager.getLogger(LocationController.class);

    @Autowired
    private LocationFacade locationFacade;

    @ApiOperation(value = "Add new location")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = LocationExceptionType.class),
            @ApiResponse(code = 400, message = "INVALID_DATA", response = LocationExceptionType.class),
    })
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationExceptionType> addLocation(@RequestBody @Validated List<LocationData> locationDataList, BindingResult result, Principal principal) {

        logger.info("------------------LOGGING  addLocation-------------------");
        logger.info("DEVICE CODE: {}", principal.getName());
        if (result.hasErrors()) {
            throw new LocationServiceException("Invalid data on adding location", HttpStatus.BAD_REQUEST, LocationExceptionType.INVALID_DATA);
        }
        locationFacade.saveLocations(locationDataList, principal.getName());
        logger.info("-----------------SUCCESSFUL addLocation-----------------");
        return new ResponseEntity<>(LocationExceptionType.SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "Find latest locations of all my devices")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = LatestLocationsData.class),
    })
    @RequestMapping(value = "/latest",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LatestLocationsData> findLatestLocations(Principal principal) {

        logger.info("------------------LOGGING  findLatestLocations-------------------");
        logger.info("USERNAME: {}", principal.getName());
        LatestLocationsData latestLocationsData = locationFacade.findLatestLocations();
        logger.info("-----------------SUCCESSFUL findLatestLocations-----------------");
        return new ResponseEntity<>(latestLocationsData, HttpStatus.OK);
    }

    @ApiOperation(value = "Find all locations by period time and device code")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = FilteredLocationsData.class),
    })
    @RequestMapping(value = "/{deviceCode}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"start", "end"})
    public ResponseEntity<FilteredLocationsData> findAllLocationsByPeriodTimeAndDeviceCode(
            @PathVariable("deviceCode") String deviceCode,
            @RequestParam("start") Long startDate,
            @RequestParam("end") Long endDate,
            Principal principal) {

        logger.info("------------------LOGGING  findLatestLocations-------------------");
        logger.info("USERNAME: {}", principal.getName());
        FilteredLocationsData filteredLocationsData = locationFacade.findAllLocationsByPeriodTimeAndDeviceCode(deviceCode, startDate, endDate, principal.getName());
        logger.info("-----------------SUCCESSFUL findLatestLocations-----------------");
        return new ResponseEntity<>(filteredLocationsData, HttpStatus.OK);
    }

    @ExceptionHandler({LocationServiceException.class})
    @ResponseBody
    public ResponseEntity<LocationExceptionType> handleException(LocationServiceException exception) {

        logger.error("------------------LOGGING  handleException------------------");
        logger.error("Code: {}", exception.getStatus());
        logger.error("Message: {}", exception.getReason());
        logger.error("-----------------SUCCESSFUL handleException-----------------");

        return new ResponseEntity<>(exception.getType(), new HttpHeaders(), exception.getStatus());
    }
}
