package com.andrei.mobiletracker.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountRole {

    private Integer id;
    private UserAccountRoleType type;
}
