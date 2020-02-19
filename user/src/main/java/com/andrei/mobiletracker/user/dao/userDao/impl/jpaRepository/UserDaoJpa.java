package com.andrei.mobiletracker.user.dao.userDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.jpaUtil.ModelJpaPersistenceConverter;
import com.andrei.mobiletracker.user.dao.userDao.UserDao;
import com.andrei.mobiletracker.user.model.MyUser;
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
    public MyUser saveOneUser(MyUser myUser) {

        logger.info("------------------LOGGING  saveOneUser------------------");
        MyUser savedUser = null;
        try {
            if (!userJpaRepository.findById(myUser.getUsername()).isPresent()) {
                userJpaRepository.saveAndFlush(modelJpaPersistenceConverter.convertMyUserToMyUserPersistence(myUser));
                savedUser = myUser;
            }
        } catch (Exception ex) {
            logger.error("------------------ERROR saveOneUser------------------");
            logger.error("username: {}", myUser.getUsername());
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        logger.info("-----------------SUCCESSFUL saveOneUser-----------------");
        return savedUser;
    }

    @Override
    public MyUser findOneMyUserByUsername(String username) {

        logger.info("------------------LOGGING  findOneMyUserByUsername------------------");
        MyUser savedUser = modelJpaPersistenceConverter.convertMyUserPersistenceToMyUser(userJpaRepository.findById(username).orElse(null));
        logger.info("-----------------SUCCESSFUL findOneMyUserByUsername-----------------");
        return savedUser;
    }
}
