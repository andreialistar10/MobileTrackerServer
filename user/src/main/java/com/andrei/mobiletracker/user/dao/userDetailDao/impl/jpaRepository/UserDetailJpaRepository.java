package com.andrei.mobiletracker.user.dao.userDetailDao.impl.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserDetailJpaRepository extends JpaRepository<UserAccountDetailPersistence, String> {

    UserAccountDetailPersistence findByUsername(String username);
}
