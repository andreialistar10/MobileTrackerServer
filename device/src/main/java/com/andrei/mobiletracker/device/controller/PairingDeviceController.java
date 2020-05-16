package com.andrei.mobiletracker.device.controller;

import com.andrei.mobiletracker.device.service.exception.DeviceExceptionType;
import com.andrei.mobiletracker.device.service.exception.DeviceServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pairing-device")
public class PairingDeviceController {

    private static final Logger logger = LogManager.getLogger(PairingDeviceController.class);

//    @ApiOperation(value = "Enroll a new device")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "SUCCESS", response = UnregisteredDevice.class),
//            @ApiResponse(code = 400, message = "INVALID_DATA", response = DeviceExceptionType.class),
//    })
//    @RequestMapping(value = "",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<DevicePairDataResponse> enrollNewDevice (@RequestBody @Validated DevicePairDataRequest devicePairDataRequest, BindingResult result) {
//
//        logger.info("------------------LOGGING  enrollNewDevice------------------");
//        logger.info("ID:       {}", devicePairDataRequest.getId());
//        logger.info("PASSWORD: {}", devicePairDataRequest.getPassword());
//
//        if (result.hasErrors()) {
//            throw new DeviceServiceException("Invalid device pair data request!", HttpStatus.BAD_REQUEST, DeviceExceptionType.INVALID_DATA);
//        }
//        DevicePairDataResponse devicePairDataResponse = unregisteredDeviceFacade.addUnregisteredDevice(unregisteredDeviceDataRequest);
//
//        logger.info("-----------------SUCCESSFUL enrollNewDevice-----------------");
//
//        return new ResponseEntity<>(devicePairDataResponse, HttpStatus.OK);
//    }

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
