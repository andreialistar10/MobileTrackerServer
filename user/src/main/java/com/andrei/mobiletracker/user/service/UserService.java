package com.andrei.mobiletracker.user.service;

import com.andrei.mobiletracker.user.dto.user.ActivatedUserDto;
import com.andrei.mobiletracker.user.dto.user.UpdatableUserAccountDetailsData;
import com.andrei.mobiletracker.user.dto.user.UserAccountDetailRequestDto;
import com.andrei.mobiletracker.user.dto.user.UserAccountDetailsData;
import com.andrei.mobiletracker.user.model.UserAccountDetails;

public interface UserService {

    UserAccountDetails signup(UserAccountDetailRequestDto userDetailDto);

    ActivatedUserDto activateAccount(String token);

    void resendRegistrationAccount(String name);

    UserAccountDetailsData getUserAccountDetails(String username);

    UserAccountDetailsData updateUserAccountDetails(UpdatableUserAccountDetailsData updatableUserAccountDetailsData, String username);
}
