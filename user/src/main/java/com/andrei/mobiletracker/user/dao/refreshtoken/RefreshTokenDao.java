package com.andrei.mobiletracker.user.dao.refreshtoken;

import com.andrei.mobiletracker.user.model.RefreshToken;

public interface RefreshTokenDao {

    RefreshToken findOneRefreshTokenByUsernameAndJwtRefreshToken(String username, String jwtRefreshToken);

    long deleteOneRefreshTokenByToken(String refreshToken);

    RefreshToken saveOneRefreshToken(RefreshToken refreshToken);
}
