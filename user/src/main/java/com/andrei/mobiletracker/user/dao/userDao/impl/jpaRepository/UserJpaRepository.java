package com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository.UserAccountRolePersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserJpaRepository extends JpaRepository<UserAccountPersistence, String> {

    @Modifying
    @Query("UPDATE UserAccountPersistence myUser set myUser.role = :givenRole where myUser.username = :username")
    int updateUserRoleByUsername(@Param("givenRole") UserAccountRolePersistence role, @Param("username") String username);
}
