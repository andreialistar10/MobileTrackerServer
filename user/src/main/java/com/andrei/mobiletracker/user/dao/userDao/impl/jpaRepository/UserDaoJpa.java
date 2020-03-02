package com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.jpaUtil.ModelJpaPersistenceConverter;
import com.andrei.mobiletracker.user.dao.userDao.UserDao;
import com.andrei.mobiletracker.user.dao.userRoleDao.impl.jpaRepository.UserAccountRolePersistence;
import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories(basePackageClasses = UserJpaRepository.class)
public class UserDaoJpa implements UserDao {

    private UserJpaRepository userJpaRepository;
    private final ModelJpaPersistenceConverter modelJpaPersistenceConverter;
    private static final Logger logger = LogManager.getLogger(UserDaoJpa.class);

    public UserDaoJpa(UserJpaRepository userJpaRepository, ModelJpaPersistenceConverter modelJpaPersistenceConverter) {

        logger.info("------------------INIT  UserDaoJpa------------------");
        this.userJpaRepository = userJpaRepository;
        this.modelJpaPersistenceConverter = modelJpaPersistenceConverter;
        logger.info("-------------SUCCESSFUL INIT UserDaoJpa-------------");
    }

    @Override
    public UserAccount saveOneUser(UserAccount userAccount) {

        logger.info("------------------LOGGING  saveOneUser------------------");
        UserAccount savedUser = null;
        try {
            if (!userJpaRepository.findById(userAccount.getUsername()).isPresent()) {
                userJpaRepository.saveAndFlush(modelJpaPersistenceConverter.convertMyUserToMyUserPersistence(userAccount));
                savedUser = userAccount;
            }
        } catch (Exception ex) {
            logger.error("------------------ERROR saveOneUser------------------");
            logger.error("username: {}", userAccount.getUsername());
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        logger.info("-----------------SUCCESSFUL saveOneUser-----------------");
        return savedUser;
    }

    @Override
    public UserAccount findOneMyUserByUsername(String username) {

        logger.info("------------------LOGGING  findOneMyUserByUsername------------------");
        UserAccount savedUser = modelJpaPersistenceConverter.convertMyUserPersistenceToMyUser(userJpaRepository.findById(username).orElse(null));
        logger.info("-----------------SUCCESSFUL findOneMyUserByUsername-----------------");
        return savedUser;
    }

    @Override
    public long updateUserStatusByUsername(String username, UserAccountRole userAccountRole) {

        logger.info("------------------LOGGING  findOneMyUserByUsername------------------");
        UserAccountRolePersistence userAccountRolePersistence = modelJpaPersistenceConverter.convertMyUserRoleToMyUserRolePersistence(userAccountRole);
        long updatedNumberOfRows = userJpaRepository.updateUserRoleByUsername(userAccountRolePersistence, username);
        logger.info("-----------------SUCCESSFUL findOneMyUserByUsername-----------------");
        return updatedNumberOfRows;
    }

}
