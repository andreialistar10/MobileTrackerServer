package com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository.MyUserRolePersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserJpaRepository extends JpaRepository<MyUserPersistence, String> {

    @Modifying
    @Query("UPDATE MyUserPersistence myUser set myUser.role = :givenRole where myUser.username = :username")
    int updateUserRoleByUsername(@Param("givenRole") MyUserRolePersistence role, @Param("username") String username);
}
