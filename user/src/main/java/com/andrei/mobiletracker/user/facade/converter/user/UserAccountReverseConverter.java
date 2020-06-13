package com.andrei.mobiletracker.user.facade.converter.user;

import com.andrei.mobiletracker.user.dto.user.UserAccountData;
import com.andrei.mobiletracker.user.facade.converter.Converter;
import com.andrei.mobiletracker.user.facade.populator.Populator;
import com.andrei.mobiletracker.user.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccountReverseConverter implements Converter<UserAccountData, UserAccount> {

    @Autowired
    private Populator<UserAccount, UserAccountData> userAccountPopulator;

    @Override
    public UserAccount convert(UserAccountData userAccountData) {

        UserAccount userAccount = new UserAccount();
        userAccountPopulator.populate(userAccountData, userAccount);
        return userAccount;
    }
}
