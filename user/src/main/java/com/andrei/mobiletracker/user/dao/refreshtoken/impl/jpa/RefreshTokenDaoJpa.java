package com.andrei.mobiletracker.user.dao.refreshtoken.impl.jpa;

import com.andrei.mobiletracker.user.dao.refreshtoken.RefreshTokenDao;
import com.andrei.mobiletracker.user.model.RefreshToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories(basePackageClasses = RefreshTokenJpaRepository.class)
public class RefreshTokenDaoJpa implements RefreshTokenDao {

    private final RefreshTokenJpaRepository jpaRepository;
    private static final Logger logger = LogManager.getLogger(RefreshTokenDaoJpa.class);


    public RefreshTokenDaoJpa(RefreshTokenJpaRepository jpaRepository) {

        logger.info("------------------INIT  RefreshTokenDaoJpa------------------");
        this.jpaRepository = jpaRepository;
        logger.info("-------------SUCCESSFUL INIT RefreshTokenDaoJpa-------------");
    }

    @Override
    public RefreshToken findOneRefreshTokenByUsernameAndJwtRefreshToken(String username, String jwtRefreshToken) {

        logger.info("------------------LOGGING  findOneRefreshTokenByUsername------------------");
        RefreshToken refreshToken = jpaRepository.findOneByUserAccountUsernameAndToken(username, jwtRefreshToken);
        logger.info("-----------------SUCCESSFUL findOneRefreshTokenByUsername-----------------");
        return refreshToken;
    }

    @Override
    public long deleteOneRefreshTokenByToken(String refreshToken) {

        logger.info("------------------LOGGING  deleteOneRefreshToken------------------");
        long numberOfRows = jpaRepository.deleteByToken(refreshToken);
        jpaRepository.flush();
        logger.info("-----------------SUCCESSFUL deleteOneRefreshToken-----------------");
        return numberOfRows;
    }

    @Override
    public RefreshToken saveOneRefreshToken(RefreshToken refreshToken) {

        logger.info("------------------LOGGING  saveOneRefreshToken------------------");
        RefreshToken savedRefreshToken = jpaRepository.save(refreshToken);
        logger.info("-----------------SUCCESSFUL saveOneRefreshToken-----------------");
        return savedRefreshToken;
    }
}
