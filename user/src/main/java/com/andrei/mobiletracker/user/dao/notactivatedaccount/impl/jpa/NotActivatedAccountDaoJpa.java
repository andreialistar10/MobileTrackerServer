package com.andrei.mobiletracker.user.dao.notactivatedaccount.impl.jpa;

import com.andrei.mobiletracker.user.dao.notactivatedaccount.NotActivatedAccountDao;
import com.andrei.mobiletracker.user.model.NotActivatedAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableJpaRepositories(basePackageClasses = NotActivatedAccountJpaRepository.class)
public class NotActivatedAccountDaoJpa implements NotActivatedAccountDao {

    private final NotActivatedAccountJpaRepository notActivatedAccountJpaRepository;
    private static final Logger logger = LogManager.getLogger(NotActivatedAccountDaoJpa.class);

    public NotActivatedAccountDaoJpa(NotActivatedAccountJpaRepository notActivatedAccountJpaRepository) {

        logger.info("---------------INIT  NotImplementedAccountDaoJpa---------------");
        this.notActivatedAccountJpaRepository = notActivatedAccountJpaRepository;
        logger.info("----------SUCCESSFUL INIT NotImplementedAccountDaoJpa----------");
    }

    @Override
    public NotActivatedAccount saveOneNotActivatedAccount(NotActivatedAccount notActivatedAccount) {

        logger.info("------------------LOGGING  saveOneNotActivatedAccount------------------");
        NotActivatedAccount savedNotActivatedAccount = null;
        if (notActivatedAccountJpaRepository.findByToken(notActivatedAccount.getToken()) == null) {
            savedNotActivatedAccount = addNewNotActivatedAccount(notActivatedAccount);
        }
        logger.info("-------------------FINAL saveOneNotActivatedAccount--------------------");
        return savedNotActivatedAccount;
    }

    @Override
    public NotActivatedAccount updateOneNotActivatedAccount(NotActivatedAccount notActivatedAccount) {

        NotActivatedAccount updatedNotActivatedAccount = null;
        logger.info("------------------LOGGING  updateOneNotActivatedAccount------------------");
        try {
            if (notActivatedAccountJpaRepository.findByToken(notActivatedAccount.getToken()) == null) {
                updatedNotActivatedAccount = addNewNotActivatedAccount(notActivatedAccount);
            } else {
                updatedNotActivatedAccount = updateNewNotActivatedAccount(notActivatedAccount);
            }
            logger.info("-------------------FINAL updateOneNotActivatedAccount--------------------");
        } catch (Exception e) {
            logger.error("ERROR when trying to update not activated account for user: {}. Message: {}", notActivatedAccount.getUsername(), e.getMessage());
            e.printStackTrace();
        }
        return updatedNotActivatedAccount;
    }

    @Override
    public NotActivatedAccount findOneNotActivatedAccountByToken(String token) {

        logger.info("------------------LOGGING  findOneNotActivatedAccountByToken------------------");
        NotActivatedAccount notActivatedAccountPersistence = notActivatedAccountJpaRepository.findByToken(token);
        logger.info("-------------------FINAL findOneNotActivatedAccountByToken--------------------");
        return notActivatedAccountPersistence;
    }

    @Override
    @Transactional
    public NotActivatedAccount deleteOneNotActivatedAccount(NotActivatedAccount notActivatedAccount) {

        logger.info("------------------LOGGING  deleteOneNotActivatedAccount------------------");
        NotActivatedAccount deletedNotActivatedAccount = null;
        if (notActivatedAccountJpaRepository.deleteByToken(notActivatedAccount.getToken()) == 1)
            deletedNotActivatedAccount = notActivatedAccount;
        logger.info("-------------------FINAL deleteOneNotActivatedAccount--------------------");
        return deletedNotActivatedAccount;
    }

    private NotActivatedAccount addNewNotActivatedAccount(NotActivatedAccount notActivatedAccount) {

        notActivatedAccountJpaRepository.save(notActivatedAccount);
        return notActivatedAccount;
    }

    private NotActivatedAccount updateNewNotActivatedAccount(NotActivatedAccount notActivatedAccount) {

        NotActivatedAccount updatedNotActivatedAccount = null;
        if (notActivatedAccountJpaRepository.updateTokenByUsername(notActivatedAccount.getToken(), notActivatedAccount.getUsername()) > 0)
            updatedNotActivatedAccount = notActivatedAccount;
        return updatedNotActivatedAccount;
    }
}