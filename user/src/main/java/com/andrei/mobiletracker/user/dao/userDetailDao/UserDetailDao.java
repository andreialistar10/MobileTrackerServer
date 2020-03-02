package com.andrei.mobiletracker.user.dao.userDetailDao;

import com.andrei.mobiletracker.user.model.UserAccountDetail;

public interface UserDetailDao {

    UserAccountDetail saveOneUserDetail(UserAccountDetail userAccountDetail);

    UserAccountDetail findOneMyUserDetailByUsername(String username);
}
