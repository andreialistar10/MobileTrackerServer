package com.andrei.mobiletracker.user.dao.notActivatedAccountDao.impl.jpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

interface NotActivatedAccountJpaRepository extends JpaRepository<NotActivatedAccountPersistence, String> {

    NotActivatedAccountPersistence findByToken(String token);

    long deleteByToken(String token);
}
