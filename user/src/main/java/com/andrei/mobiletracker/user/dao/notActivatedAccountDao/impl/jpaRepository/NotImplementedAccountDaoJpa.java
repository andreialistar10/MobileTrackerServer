package com.andrei.mobiletracker.user.dao.notActivatedAccountDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.jpaUtil.ModelJpaPersistenceConverter;
import com.andrei.mobiletracker.user.dao.notActivatedAccountDao.NotActivatedAccountDao;
import com.andrei.mobiletracker.user.model.NotActivatedAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableJpaRepositories(basePackageClasses = NotActivatedAccountJpaRepository.class)
public class NotImplementedAccountDaoJpa implements NotActivatedAccountDao {

    private final NotActivatedAccountJpaRepository notActivatedAccountJpaRepository;
    private final ModelJpaPersistenceConverter modelJpaPersistenceConverter;
    private static final Logger logger = LogManager.getLogger(NotImplementedAccountDaoJpa.class);

    public NotImplementedAccountDaoJpa(NotActivatedAccountJpaRepository notActivatedAccountJpaRepository, ModelJpaPersistenceConverter modelJpaPersistenceConverter) {

        logger.info("---------------INIT  NotImplementedAccountDaoJpa---------------");
        this.notActivatedAccountJpaRepository = notActivatedAccountJpaRepository;
        this.modelJpaPersistenceConverter = modelJpaPersistenceConverter;
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
            NotActivatedAccountPersistence notActivatedAccountPersistence = modelJpaPersistenceConverter.convertNotActivatedAccountToNotActivatedAccountPersistence(notActivatedAccount);
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
        NotActivatedAccountPersistence notActivatedAccountPersistence = notActivatedAccountJpaRepository.findByToken(token);
        logger.info("-------------------FINAL findOneNotActivatedAccountByToken--------------------");
        return modelJpaPersistenceConverter.convertNotActivatedAccountPersistenceToNotActivatedAccount(notActivatedAccountPersistence);
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

        NotActivatedAccountPersistence notActivatedAccountPersistence = modelJpaPersistenceConverter.convertNotActivatedAccountToNotActivatedAccountPersistence(notActivatedAccount);
        notActivatedAccountJpaRepository.save(notActivatedAccountPersistence);
        return notActivatedAccount;
    }

    private NotActivatedAccount updateNewNotActivatedAccount(NotActivatedAccount notActivatedAccount) {

        NotActivatedAccountPersistence notActivatedAccountPersistence = modelJpaPersistenceConverter.convertNotActivatedAccountToNotActivatedAccountPersistence(notActivatedAccount);
        if (notActivatedAccountJpaRepository.updateTokenByUsername(notActivatedAccount.getToken(), notActivatedAccount.getUsername()) == 0)
            return null;
        return notActivatedAccount;
    }
}
