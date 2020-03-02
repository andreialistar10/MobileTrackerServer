package com.andrei.mobiletracker.user.dao.userDetailDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.jpaUtil.ModelJpaPersistenceConverter;
import com.andrei.mobiletracker.user.dao.userDetailDao.UserDetailDao;
import com.andrei.mobiletracker.user.model.UserAccountDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories(basePackageClasses = UserDetailJpaRepository.class)
public class UserDetailDaoJpa implements UserDetailDao {

    private UserDetailJpaRepository userDetailJpaRepository;
    private final ModelJpaPersistenceConverter modelJpaPersistenceConverter;
    private static final Logger logger = LogManager.getLogger(UserDetailDaoJpa.class);

    public UserDetailDaoJpa(UserDetailJpaRepository userDetailJpaRepository, ModelJpaPersistenceConverter modelJpaPersistenceConverter) {

        logger.info("------------------INIT  UserDetailDaoJpa------------------");
        this.userDetailJpaRepository = userDetailJpaRepository;
        this.modelJpaPersistenceConverter = modelJpaPersistenceConverter;
        logger.info("-------------SUCCESSFUL INIT UserDetailDaoJpa-------------");
    }

    @Override
    public UserAccountDetail saveOneUserDetail(UserAccountDetail userAccountDetail) {

        logger.info("------------------LOGGING  saveOneUserDetail------------------");
        UserAccountDetail savedUserDetail = null;
        try {
            if (userDetailJpaRepository.findByUsername(userAccountDetail.getUser().getUsername()) == null) {
                userDetailJpaRepository.save(modelJpaPersistenceConverter.convertMyUserDetailToMyUserDetailPersistence(userAccountDetail));
                savedUserDetail = userAccountDetail;
            }
        } catch (Exception ex) {
            logger.error("------------------ERROR saveOneUserDetail------------------");
            logger.error("username: {}", userAccountDetail.getUser().getUsername());
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        logger.info("-----------------SUCCESSFUL saveOneUserDetail-----------------");
        return savedUserDetail;
    }

    @Override
    public UserAccountDetail findOneMyUserDetailByUsername(String username) {

        logger.info("------------------LOGGING  findOneMyUserDetailByUsername------------------");
        UserAccountDetail userAccountDetail = modelJpaPersistenceConverter.convertMyUserDetailPersistenceToMyUserDetail(userDetailJpaRepository.findByUsername(username));
        logger.info("-----------------SUCCESSFUL findOneMyUserDetailByUsername-----------------");
        return userAccountDetail;

    }
}
