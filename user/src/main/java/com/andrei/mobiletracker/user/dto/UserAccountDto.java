package com.andrei.mobiletracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
