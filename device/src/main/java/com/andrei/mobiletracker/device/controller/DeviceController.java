package com.andrei.mobiletracker.device.controller;

import com.andrei.mobiletracker.device.dto.device.DeviceData;
import com.andrei.mobiletracker.device.dto.device.DevicesData;
import com.andrei.mobiletracker.device.dto.device.UpdateDeviceData;
import com.andrei.mobiletracker.device.facade.device.DeviceFacade;
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

import java.security.Principal;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private static final Logger logger = LogManager.getLogger(DeviceController.class);

    @Autowired
    private DeviceFacade deviceFacade;

    @ApiOperation(value = "Get all devices")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = DevicesData.class),
    })
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DevicesData> findAllDevices(
            @RequestParam(name = "id-only", required = false) boolean idOnly,
            @RequestParam(name = "allow-deleted", required = false) boolean allowDeleted,
            Principal principal) {

        logger.info("------------------LOGGING  findAllMyDevices------------------");
        logger.info("username: {}", principal.getName());
        DevicesData devicesData = deviceFacade.findAllDevicesByOwnerUsername(principal.getName(), idOnly, allowDeleted);
        logger.info("-----------------SUCCESSFUL findAllMyDevices-----------------");
        return new ResponseEntity<>(devicesData, HttpStatus.OK);
    }

    @ApiOperation(value = "Get device by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = DeviceData.class),
            @ApiResponse(code = 404, message = "DEVICE_NOT_FOUND", response = DeviceExceptionType.class)
    })
    @RequestMapping(value = "/{deviceCode}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceData> findDeviceById(@PathVariable(name = "deviceCode") String deviceCode, Principal principal) {

        logger.info("------------------LOGGING  findDevice------------------");
        logger.info("username: {}", principal.getName());
        DeviceData deviceData = deviceFacade.findDeviceById(deviceCode, principal.getName());
        logger.info("-----------------SUCCESSFUL findDevice-----------------");
        return new ResponseEntity<>(deviceData, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete device")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = DeviceData.class),
            @ApiResponse(code = 404, message = "DEVICE_NOT_FOUND", response = DeviceExceptionType.class)
    })
    @RequestMapping(value = "/{deviceCode}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceData> deleteDeviceById(@PathVariable(name = "deviceCode") String deviceCode, Principal principal) {

        logger.info("------------------LOGGING  deleteDeviceById------------------");
        logger.info("username: {}", principal.getName());
        DeviceData deviceData = deviceFacade.deleteDeviceByIdAndOwnerUsername(deviceCode, principal.getName());
        logger.info("-----------------SUCCESSFUL deleteDeviceById-----------------");
        return new ResponseEntity<>(deviceData, HttpStatus.OK);
    }

    @ApiOperation(value = "Update device")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = DeviceData.class),
            @ApiResponse(code = 400, message = "INVALID_DATA", response = DeviceExceptionType.class),
            @ApiResponse(code = 404, message = "DEVICE_NOT_FOUND", response = DeviceExceptionType.class)
    })
    @RequestMapping(value = "/{deviceCode}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceData> updateDeviceById(
            @Validated @RequestBody UpdateDeviceData updateDeviceData,
            BindingResult result,
            @PathVariable(name = "deviceCode") String deviceCode,
            Principal principal) {

        logger.info("------------------LOGGING  updateDeviceById------------------");
        logger.info("username: {}", principal.getName());
        logger.info("device:   {}", deviceCode);
        if (result.hasErrors()) {
            throw new DeviceServiceException("Invalid device name", HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
        }
        DeviceData deviceData = deviceFacade.updateDeviceById(deviceCode, updateDeviceData, principal.getName());
        logger.info("-----------------SUCCESSFUL updateDeviceById-----------------");
        return new ResponseEntity<>(deviceData, HttpStatus.OK);
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
