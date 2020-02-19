package com.andrei.mobiletracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyUserDetailRequestDto {

    private String username;
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private String email;
}
