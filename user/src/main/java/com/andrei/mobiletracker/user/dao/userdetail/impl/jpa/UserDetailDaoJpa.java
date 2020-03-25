package com.andrei.mobiletracker.user.dao.userdetail.impl.jpa;

import com.andrei.mobiletracker.user.dao.userdetail.UserDetailDao;
import com.andrei.mobiletracker.user.model.UserAccountDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@Component
@EnableJpaRepositories(basePackageClasses = UserDetailJpaRepository.class)
public class UserDetailDaoJpa implements UserDetailDao {

    private UserDetailJpaRepository userDetailJpaRepository;
    private static final Logger logger = LogManager.getLogger(UserDetailDaoJpa.class);

    public UserDetailDaoJpa(UserDetailJpaRepository userDetailJpaRepository) {

        logger.info("------------------INIT  UserDetailDaoJpa------------------");
        this.userDetailJpaRepository = userDetailJpaRepository;
        logger.info("-------------SUCCESSFUL INIT UserDetailDaoJpa-------------");
    }

    @Override
    public UserAccountDetails saveOneUserDetail(UserAccountDetails userAccountDetails) {

        logger.info("------------------LOGGING  saveOneUserDetail------------------");
        UserAccountDetails savedUserDetail = null;
        try {
            if (userDetailJpaRepository.findByUsername(userAccountDetails.getUsername()) == null) {
                userDetailJpaRepository.save(userAccountDetails);
                savedUserDetail = userAccountDetails;
            }
        } catch (Exception ex) {
            logger.error("------------------ERROR saveOneUserDetail------------------");
            logger.error("username: {}", userAccountDetails.getUser().getUsername());
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        logger.info("-----------------SUCCESSFUL saveOneUserDetail-----------------");
        return savedUserDetail;
    }

    @Override
    public UserAccountDetails findOneMyUserDetailByUsername(String username) {

        logger.info("------------------LOGGING  findOneMyUserDetailByUsername------------------");
        UserAccountDetails userAccountDetails = userDetailJpaRepository.findByUsername(username);
        logger.info("-----------------SUCCESSFUL findOneMyUserDetailByUsername-----------------");
        return userAccountDetails;

    }
}
