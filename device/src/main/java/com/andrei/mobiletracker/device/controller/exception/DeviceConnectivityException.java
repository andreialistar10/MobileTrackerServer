package com.andrei.mobiletracker.device.controller.exception;

import lombok.Getter;

@Getter
public class DeviceConnectivityException extends RuntimeException {

    private String topic;

    public DeviceConnectivityException(String message, String topic) {
        super(message);
        this.topic = topic;
    }


}
