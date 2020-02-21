package com.andrei.mobiletracker.user.dao.userDao;

import com.andrei.mobiletracker.user.model.MyUser;
import com.andrei.mobiletracker.user.model.MyUserRole;

public interface UserDao {

    MyUser saveOneUser(MyUser myUser);

    MyUser findOneMyUserByUsername(String username);

    long updateUserStatusByUsername(String username, MyUserRole myUserRole);
}
