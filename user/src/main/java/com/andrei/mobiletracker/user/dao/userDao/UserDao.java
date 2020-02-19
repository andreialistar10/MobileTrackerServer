package com.andrei.mobiletracker.user.dao.userDao;

import com.andrei.mobiletracker.user.model.MyUser;

public interface UserDao {

    MyUser saveOneUser(MyUser myUser);

    MyUser findOneMyUserByUsername(String username);
}
