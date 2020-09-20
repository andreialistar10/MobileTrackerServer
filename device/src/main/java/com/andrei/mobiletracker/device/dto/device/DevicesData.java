package com.andrei.mobiletracker.device.dto.device;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevicesData {

    private List<?> devices;
}
