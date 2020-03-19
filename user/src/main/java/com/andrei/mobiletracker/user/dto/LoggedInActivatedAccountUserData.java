package com.andrei.mobiletracker.user.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInActivatedAccountUserData extends LoggedInUserData {

    private String refreshToken;

    public LoggedInActivatedAccountUserData(String jwt, String role, String refreshToken) {
        super(jwt,role);
        this.refreshToken = refreshToken;
    }
}
