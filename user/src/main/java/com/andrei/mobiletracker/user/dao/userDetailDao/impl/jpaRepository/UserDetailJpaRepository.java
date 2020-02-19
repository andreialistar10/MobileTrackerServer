package com.andrei.mobiletracker.user.dao.userDetailDao.impl.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserDetailJpaRepository extends JpaRepository<MyUserDetailPersistence, String> {

    MyUserDetailPersistence findByUsername(String username);
}
