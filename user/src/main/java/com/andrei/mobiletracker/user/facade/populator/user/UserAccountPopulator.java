package com.andrei.mobiletracker.user.facade.populator.user;

import com.andrei.mobiletracker.user.dto.user.UserAccountData;
import com.andrei.mobiletracker.user.facade.populator.Populator;
import com.andrei.mobiletracker.user.model.UserAccount;
import org.springframework.stereotype.Component;

@Component
public class UserAccountPopulator implements Populator<UserAccount, UserAccountData> {

    @Override
    public void populate(UserAccountData userAccountData, UserAccount userAccount) {

        userAccount.setUsername(userAccountData.getUsername());
        userAccount.setPassword(userAccountData.getPassword());
    }
}
