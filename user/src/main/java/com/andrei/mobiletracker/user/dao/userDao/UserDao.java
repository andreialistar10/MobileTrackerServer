package com.andrei.mobiletracker.user.dao.userDao;

import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountRole;

public interface UserDao {

    UserAccount saveOneUser(UserAccount userAccount);

    UserAccount findOneMyUserByUsername(String username);

    long updateUserStatusByUsername(String username, UserAccountRole userAccountRole);
}
