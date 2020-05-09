package com.andrei.mobiletracker.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnregisteredDeviceDataRequest {

    @NotBlank
    @Length(max = 50)
    private String name;
}
