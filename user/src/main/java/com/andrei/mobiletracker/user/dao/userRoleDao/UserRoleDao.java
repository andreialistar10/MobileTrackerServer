package com.andrei.mobiletracker.user.dao.userRoleDao;

import com.andrei.mobiletracker.user.model.MyUserRole;
import com.andrei.mobiletracker.user.model.MyUserRoleType;

public interface UserRoleDao {

    MyUserRole findOneUserRoleByType(MyUserRoleType type);
}
