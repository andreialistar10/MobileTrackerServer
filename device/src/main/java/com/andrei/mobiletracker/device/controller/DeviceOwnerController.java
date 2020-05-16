package com.andrei.mobiletracker.device.controller;

import com.andrei.mobiletracker.device.dto.ownerdevice.DevicesData;
import com.andrei.mobiletracker.device.facade.device.DeviceFacade;
import com.andrei.mobiletracker.device.model.UnregisteredDevice;
import com.andrei.mobiletracker.device.service.exception.DeviceExceptionType;
import com.andrei.mobiletracker.device.service.exception.DeviceServiceException;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/my-devices")
public class DeviceOwnerController {

    private static final Logger logger = LogManager.getLogger(DeviceOwnerController.class);

    @Autowired
    private DeviceFacade deviceFacade;

    @ApiOperation(value = "Get all my devices")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UnregisteredDevice.class),
    })
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DevicesData> findAllMyDevices(Principal principal) {

        logger.info("------------------LOGGING  findAllMyDevices------------------");
        logger.info("username: {}", principal.getName());
        DevicesData devicesData = deviceFacade.findAllDevicesByOwnerUsername(principal.getName());
        logger.info("-----------------SUCCESSFUL findAllMyDevices-----------------");
        return new ResponseEntity<>(devicesData, HttpStatus.OK);
    }

    @ExceptionHandler({DeviceServiceException.class})
    @ResponseBody
    public ResponseEntity<DeviceExceptionType> handleException(DeviceServiceException exception) {

        logger.error("------------------LOGGING  handleException------------------");
        logger.error("Code: {}", exception.getStatus());
        logger.error("Message: {}", exception.getReason());
        logger.error("-----------------SUCCESSFUL handleException-----------------");

        return new ResponseEntity<>(exception.getType(), new HttpHeaders(), exception.getStatus());
    }
}