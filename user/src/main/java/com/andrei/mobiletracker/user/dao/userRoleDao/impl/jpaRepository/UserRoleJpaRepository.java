package com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.model.UserAccountRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleJpaRepository extends JpaRepository<UserAccountRolePersistence, Integer> {

    UserAccountRolePersistence findByName(UserAccountRoleType name);
}
