package com.andrei.mobiletracker.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountDetailResponseDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
