package com.andrei.mobiletracker.user.service;

import com.andrei.mobiletracker.user.model.RefreshToken;
import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountToken;

public interface LoginService {

    UserAccountToken login(UserAccount userAccount);

    RefreshToken findOneRefreshTokenByUsernameAndJwtRefreshToken(String username, String jwtRefreshToken);

    UserAccountToken generateNewRefreshToken(RefreshToken refreshToken);

    void logout(RefreshToken refreshToken);
}
