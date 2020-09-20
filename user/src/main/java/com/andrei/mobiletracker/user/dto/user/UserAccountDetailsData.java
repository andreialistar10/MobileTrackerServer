package com.andrei.mobiletracker.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountDetailsData {

    private String username;
    private String lastName;
    private String firstName;
    private String email;
}
