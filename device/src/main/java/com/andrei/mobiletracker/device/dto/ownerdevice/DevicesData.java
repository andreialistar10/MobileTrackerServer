package com.andrei.mobiletracker.device.dto.ownerdevice;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevicesData {

    private List<DeviceData> devices;
}
