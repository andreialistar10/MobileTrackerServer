package com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<MyUserPersistence,String> {

}
