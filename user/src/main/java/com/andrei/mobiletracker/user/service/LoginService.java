package com.andrei.mobiletracker.user.service;

import com.andrei.mobiletracker.user.dto.LoggedInUserDto;
import com.andrei.mobiletracker.user.dto.UserDto;

public interface LoginService {

    LoggedInUserDto login (UserDto user);
}
