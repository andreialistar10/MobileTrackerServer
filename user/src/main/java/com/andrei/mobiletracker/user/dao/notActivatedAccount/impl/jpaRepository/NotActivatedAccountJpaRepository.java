package com.andrei.mobiletracker.user.dao.notActivatedAccount.impl.jpaRepository;

import com.andrei.mobiletracker.user.model.NotActivatedAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface NotActivatedAccountJpaRepository extends JpaRepository<NotActivatedAccount, String> {

    NotActivatedAccount findByToken(String token);

    long deleteByToken(String token);

    @Modifying
    @Query("UPDATE NotActivatedAccount notActivatedAccount set notActivatedAccount.token = :token where notActivatedAccount.username = :username")
    int updateTokenByUsername(@Param("token") String token, @Param("username") String username);
}
