package com.andrei.mobiletracker.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDetail {

    private UserAccount user;
    private String firstName;
    private String lastName;
    private String email;
}
