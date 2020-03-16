package com.andrei.mobiletracker.user.service;

import com.andrei.mobiletracker.user.dto.ActivatedUserDto;
import com.andrei.mobiletracker.user.dto.UserAccountDetailRequestDto;
import com.andrei.mobiletracker.user.model.UserAccountDetails;

public interface UserService {

    UserAccountDetails signup(UserAccountDetailRequestDto userDetailDto);

    ActivatedUserDto activateAccount(String token);

    void resendRegistrationAccount(String name);
}
