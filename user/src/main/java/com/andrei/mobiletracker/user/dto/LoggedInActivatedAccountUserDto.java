package com.andrei.mobiletracker.user.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInActivatedAccountUserDto extends LoggedInUserDto {

    private String refreshToken;

    public LoggedInActivatedAccountUserDto(String jwt, String role, String refreshToken) {
        super(jwt,role);
        this.refreshToken = refreshToken;
    }
}
