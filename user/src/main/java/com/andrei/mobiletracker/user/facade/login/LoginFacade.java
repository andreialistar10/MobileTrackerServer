package com.andrei.mobiletracker.user.facade.login;

import com.andrei.mobiletracker.user.dto.login.LoggedInUserData;
import com.andrei.mobiletracker.user.dto.user.UserAccountData;

public interface LoginFacade {

    LoggedInUserData login(UserAccountData userAccountData);

    LoggedInUserData generateNewRefreshToken(String name, String jwtRefreshToken);

    void logout(String name, String jwtRefreshToken);
}
