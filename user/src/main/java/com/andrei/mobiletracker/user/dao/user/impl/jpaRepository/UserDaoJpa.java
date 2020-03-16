package com.andrei.mobiletracker.user.dao.user.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.user.UserDao;
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
    private static final Logger logger = LogManager.getLogger(UserDaoJpa.class);

    public UserDaoJpa(UserJpaRepository userJpaRepository) {

        logger.info("------------------INIT  UserDaoJpa------------------");
        this.userJpaRepository = userJpaRepository;
        logger.info("-------------SUCCESSFUL INIT UserDaoJpa-------------");
    }

    @Override
    public UserAccount saveOneUser(UserAccount userAccount) {

        logger.info("------------------LOGGING  saveOneUser------------------");
        UserAccount savedUser = null;
        try {
            if (!userJpaRepository.findById(userAccount.getUsername()).isPresent()) {
                userJpaRepository.saveAndFlush(userAccount);
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
        UserAccount savedUser = userJpaRepository.findById(username).orElse(null);
        logger.info("-----------------SUCCESSFUL findOneMyUserByUsername-----------------");
        return savedUser;
    }

    @Override
    public long updateUserStatusByUsername(String username, UserAccountRole userAccountRole) {

        logger.info("------------------LOGGING  findOneMyUserByUsername------------------");
        long updatedNumberOfRows = userJpaRepository.updateUserRoleByUsername(userAccountRole, username);
        logger.info("-----------------SUCCESSFUL findOneMyUserByUsername-----------------");
        return updatedNumberOfRows;
    }

}
