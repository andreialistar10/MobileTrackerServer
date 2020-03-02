package com.andrei.mobiletracker.user.dao.userRoleDao;

import com.andrei.mobiletracker.user.model.UserAccountRole;
import com.andrei.mobiletracker.user.model.UserAccountRoleType;

public interface UserRoleDao {

    UserAccountRole findOneUserRoleByType(UserAccountRoleType type);
}
