package com.andrei.mobiletracker.user.dao.user.impl.jpa;

import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface UserJpaRepository extends JpaRepository<UserAccount, String> {

    @Modifying
    @Query("UPDATE UserAccount myUser set myUser.role = :givenRole where myUser.username = :username")
    int updateUserRoleByUsername(@Param("givenRole") UserAccountRole role, @Param("username") String username);
}
