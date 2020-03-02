package com.andrei.mobiletracker.user.service;

import com.andrei.mobiletracker.user.dto.LoggedInUserDto;
import com.andrei.mobiletracker.user.dto.UserAccountDto;

public interface LoginService {

    LoggedInUserDto login (UserAccountDto user);
}
