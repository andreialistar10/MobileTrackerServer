package com.andrei.mobiletracker.user.dao.notActivatedAccountDao.impl.jpaRepository;

import com.andrei.mobiletracker.user.dao.jpaUtil.ModelJpaPersistenceConverter;
import com.andrei.mobiletracker.user.dao.notActivatedAccountDao.NotActivatedAccountDao;
import com.andrei.mobiletracker.user.model.NotActivatedAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

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
        if (!notActivatedAccountJpaRepository.findById(notActivatedAccount.getToken()).isPresent()){
            NotActivatedAccountPersistence notActivatedAccountPersistence = modelJpaPersistenceConverter.convertNotActivatedAccountToNotActivatedAccountPersistence(notActivatedAccount);
            notActivatedAccountJpaRepository.save(notActivatedAccountPersistence);
            savedNotActivatedAccount = notActivatedAccount;
        }
        logger.info("-------------------FINAL saveOneNotActivatedAccount--------------------");
        return savedNotActivatedAccount;
    }

    @Override
    public NotActivatedAccount findOneNotActivatedAccountByToken(String token) {

        logger.info("------------------LOGGING  findOneNotActivatedAccountByToken------------------");
        NotActivatedAccountPersistence notActivatedAccountPersistence = notActivatedAccountJpaRepository.findByToken(token);
        logger.info("-------------------FINAL findOneNotActivatedAccountByToken--------------------");
        return  modelJpaPersistenceConverter.convertNotActivatedAccountPersistenceToNotActivatedAccount(notActivatedAccountPersistence);
    }
}
