package com.andrei.mobiletracker.user.dao.userdetail.impl.jpa;

import com.andrei.mobiletracker.user.model.UserAccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserDetailJpaRepository extends JpaRepository<UserAccountDetails, String> {

    UserAccountDetails findByUsername(String username);
}
