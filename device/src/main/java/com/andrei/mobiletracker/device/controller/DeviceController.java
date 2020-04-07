package com.andrei.mobiletracker.device.controller;

import com.andrei.mobiletracker.device.dto.UnregisteredDeviceData;
import com.andrei.mobiletracker.device.facade.unregistereddevice.UnregisteredDeviceFacade;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private static final Logger logger = LogManager.getLogger(DeviceController.class);

    @Autowired
    private UnregisteredDeviceFacade unregisteredDeviceFacade;

    @ApiOperation(value = "Register a new device")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = UnregisteredDevice.class),
            @ApiResponse(code = 400, message = "INVALID_DATA", response = DeviceExceptionType.class),
    })
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnregisteredDevice> addUnregisteredDevice(@RequestBody @Validated UnregisteredDeviceData unregisteredDeviceData, BindingResult result) {

        logger.info("------------------LOGGING  addUnregisteredDevice------------------");
        logger.info("name: {}", unregisteredDeviceData.getName());
        if (result.hasErrors()) {
            throw new DeviceServiceException(String.format("\"%s\" could not be name for an unregistered device data!", unregisteredDeviceData.getName()), HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        UnregisteredDevice unregisteredDevice = unregisteredDeviceFacade.addUnregisteredDevice(unregisteredDeviceData);
        logger.info("-----------------SUCCESSFUL addUnregisteredDevice-----------------");
        return new ResponseEntity<>(unregisteredDevice, HttpStatus.OK);
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
