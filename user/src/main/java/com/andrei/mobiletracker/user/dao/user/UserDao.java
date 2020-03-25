package com.andrei.mobiletracker.user.dao.user;

import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountRole;

public interface UserDao {

    UserAccount saveOneUser(UserAccount userAccount);

    UserAccount findOneUserAccountByUsername(String username);

    long updateUserStatusByUsername(String username, UserAccountRole userAccountRole);
}
