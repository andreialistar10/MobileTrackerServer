package com.andrei.mobiletracker.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class RefreshTokenKey implements Serializable {

    private String userAccount;
    private String token;
}