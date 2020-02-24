package com.andrei.mobiletracker.user.dao.notActivatedAccountDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository.MyUserRolePersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface NotActivatedAccountJpaRepository extends JpaRepository<NotActivatedAccountPersistence, String> {

    NotActivatedAccountPersistence findByToken(String token);

    long deleteByToken(String token);

    @Modifying
    @Query("UPDATE NotActivatedAccountPersistence notActivatedAccount set notActivatedAccount.token = :token where notActivatedAccount.username = :username")
    int updateTokenByUsername(@Param("token") String token, @Param("username") String username);
}
