package com.andrei.mobiletracker.user.dao.userRole.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.userRole.UserRoleDao;
import com.andrei.mobiletracker.user.model.UserAccountRole;
import com.andrei.mobiletracker.user.model.UserAccountRoleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories(basePackageClasses = UserRoleJpaRepository.class)
public class UserRoleDaoJpa implements UserRoleDao {

    private final UserRoleJpaRepository userRoleJpaRepository;
    private static final Logger logger = LogManager.getLogger(UserRoleDaoJpa.class);

    public UserRoleDaoJpa(UserRoleJpaRepository userRoleJpaRepository) {

        logger.info("------------------INIT  UserRoleDaoJpa------------------");
        this.userRoleJpaRepository = userRoleJpaRepository;
        logger.info("-------------SUCCESSFUL INIT UserRoleDaoJpa-------------");
    }

    @Override
    public UserAccountRole findOneUserRoleByType(UserAccountRoleType type) {

        logger.info("------------------LOGGING  findOneUserRoleByName------------------");
        UserAccountRole foundUserRolePersistence = userRoleJpaRepository.findByType(type);
        logger.info("-----------------SUCCESSFUL findOneUserRoleByName-----------------");
        return foundUserRolePersistence;
    }
}
