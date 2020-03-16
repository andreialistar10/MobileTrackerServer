package com.andrei.mobiletracker.user.dao.userDetail.impl.jpaRepository;

import com.andrei.mobiletracker.user.model.UserAccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserDetailJpaRepository extends JpaRepository<UserAccountDetails, String> {

    UserAccountDetails findByUsername(String username);
}
