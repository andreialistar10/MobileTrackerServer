package com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.jpaUtil.ModelJpaPersistenceConverter;
import com.andrei.mobiletracker.user.dao.userRoleDao.UserRoleDao;
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
    private final ModelJpaPersistenceConverter modelJpaPersistenceConverter;
    private static final Logger logger = LogManager.getLogger(UserRoleDaoJpa.class);

    public UserRoleDaoJpa(UserRoleJpaRepository userRoleJpaRepository, ModelJpaPersistenceConverter modelJpaPersistenceConverter) {

        logger.info("------------------INIT  UserRoleDaoJpa------------------");
        this.userRoleJpaRepository = userRoleJpaRepository;
        this.modelJpaPersistenceConverter = modelJpaPersistenceConverter;
        logger.info("-------------SUCCESSFUL INIT UserRoleDaoJpa-------------");
    }

    @Override
    public UserAccountRole findOneUserRoleByType(UserAccountRoleType type) {

        logger.info("------------------LOGGING  findOneUserRoleByName------------------");
        UserAccountRolePersistence foundUserRolePersistence = userRoleJpaRepository.findByName(type);
        logger.info("-----------------SUCCESSFUL findOneUserRoleByName-----------------");
        return modelJpaPersistenceConverter.convertMyUserRolePersistenceToMyUserRole(foundUserRolePersistence);
    }
}
