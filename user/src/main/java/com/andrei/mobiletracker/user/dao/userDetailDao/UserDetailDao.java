package com.andrei.mobiletracker.user.dao.userDetailDao;

import com.andrei.mobiletracker.user.model.MyUserDetail;

public interface UserDetailDao {

    MyUserDetail saveOneUserDetail(MyUserDetail myUserDetail);

    MyUserDetail findOneMyUserDetailByUsername(String username);
}
