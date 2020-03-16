package com.andrei.mobiletracker.user.dao.userDetail;

import com.andrei.mobiletracker.user.model.UserAccountDetails;

public interface UserDetailDao {

    UserAccountDetails saveOneUserDetail(UserAccountDetails userAccountDetails);

    UserAccountDetails findOneMyUserDetailByUsername(String username);
}
