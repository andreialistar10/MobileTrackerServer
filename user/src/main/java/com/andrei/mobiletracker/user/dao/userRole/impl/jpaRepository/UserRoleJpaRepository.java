package com.andrei.mobiletracker.user.dao.userRole.impl.jpaRepository;

import com.andrei.mobiletracker.user.model.UserAccountRole;
import com.andrei.mobiletracker.user.model.UserAccountRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleJpaRepository extends JpaRepository<UserAccountRole, Integer> {

    UserAccountRole findByType(UserAccountRoleType name);
}
