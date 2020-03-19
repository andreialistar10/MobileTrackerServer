package com.andrei.mobiletracker.user.facade.login.impl;

import com.andrei.mobiletracker.user.dto.LoggedInActivatedAccountUserData;
import com.andrei.mobiletracker.user.dto.LoggedInUserData;
import com.andrei.mobiletracker.user.dto.UserAccountData;
import com.andrei.mobiletracker.user.facade.converter.Converter;
import com.andrei.mobiletracker.user.facade.login.LoginFacade;
import com.andrei.mobiletracker.user.model.RefreshToken;
import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountToken;
import com.andrei.mobiletracker.user.service.LoginService;
import org.springframework.stereotype.Component;

@Component
public class LoginFacadeImpl implements LoginFacade {

    private final LoginService loginService;

    private final Converter<UserAccountData, UserAccount> userAccountReverseConverter;

    public LoginFacadeImpl(LoginService loginService, Converter<UserAccountData, UserAccount> userAccountReverseConverter) {
        this.loginService = loginService;
        this.userAccountReverseConverter = userAccountReverseConverter;
    }

    @Override
    public LoggedInUserData login(UserAccountData userAccountData) {

        UserAccount userAccount = userAccountReverseConverter.convert(userAccountData);
        UserAccountToken userAccountToken = loginService.login(userAccount);
        String refreshToken = userAccountToken.getRefreshToken();
        String jwt = userAccountToken.getJwt();
        String role = userAccount.getRole().toString();
        if (refreshToken != null) {
            return new LoggedInActivatedAccountUserData(jwt, role, refreshToken);
        }
        return LoggedInUserData.builder()
                .jwt(jwt)
                .role(role)
                .build();
    }

    @Override
    public LoggedInUserData generateNewRefreshToken(String username, String jwtRefreshToken) {

        RefreshToken refreshToken = loginService.findOneRefreshTokenByUsernameAndJwtRefreshToken(username, jwtRefreshToken);
        UserAccountToken userAccountToken = loginService.generateNewRefreshToken(refreshToken);
        String role = refreshToken.getUserAccount().getRole().toString();
        return new LoggedInActivatedAccountUserData(userAccountToken.getJwt(), role, userAccountToken.getRefreshToken());
    }

    @Override
    public void logout(String username, String jwtRefreshToken) {

        RefreshToken refreshToken = loginService.findOneRefreshTokenByUsernameAndJwtRefreshToken(username, jwtRefreshToken);
        loginService.logout(refreshToken);
    }
}
