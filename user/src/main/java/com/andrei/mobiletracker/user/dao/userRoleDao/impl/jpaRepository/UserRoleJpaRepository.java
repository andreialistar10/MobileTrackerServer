package com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.model.MyUserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleJpaRepository extends JpaRepository<MyUserRolePersistence, Integer> {

    MyUserRolePersistence findByName(MyUserRoleType name);
}
