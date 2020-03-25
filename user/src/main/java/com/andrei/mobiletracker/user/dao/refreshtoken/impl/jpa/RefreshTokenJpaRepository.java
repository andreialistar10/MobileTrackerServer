package com.andrei.mobiletracker.user.dao.refreshtoken.impl.jpa;

import com.andrei.mobiletracker.user.model.RefreshToken;
import com.andrei.mobiletracker.user.model.RefreshTokenKey;
import org.springframework.data.jpa.repository.JpaRepository;

interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, RefreshTokenKey> {

    RefreshToken findOneByUserAccountUsernameAndToken(String username, String token);

    int deleteByToken(String token);
}
