package com.andrei.mobiletracker.user.service.impl;

import com.andrei.mobiletracker.user.dao.notActivatedAccountDao.NotActivatedAccountDao;
import com.andrei.mobiletracker.user.dao.userDao.UserDao;
import com.andrei.mobiletracker.user.dao.userDetailDao.UserDetailDao;
import com.andrei.mobiletracker.user.dao.userRoleDao.UserRoleDao;
import com.andrei.mobiletracker.user.dto.ActivatedUserDto;
import com.andrei.mobiletracker.user.dto.UserAccountDetailRequestDto;
import com.andrei.mobiletracker.user.model.*;
import com.andrei.mobiletracker.user.service.UserService;
import com.andrei.mobiletracker.user.service.exception.UserExceptionType;
import com.andrei.mobiletracker.user.service.exception.UserServiceException;
import com.andrei.mobiletracker.user.service.mailSender.MailUserDetail;
import com.andrei.mobiletracker.user.service.mailSender.MyMailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserDetailDao userDetailDao;
    private final UserRoleDao userRoleDao;
    private final NotActivatedAccountDao notActivatedAccountDao;
    private final MyMailSender myMailSender;
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserDao userDao, UserDetailDao userDetailDao, UserRoleDao userRoleDao, NotActivatedAccountDao notActivatedAccountDao, MyMailSender myMailSender) {

        logger.info("------------------INIT  UserServiceImpl------------------");
        this.userDao = userDao;
        this.userDetailDao = userDetailDao;
        this.userRoleDao = userRoleDao;
        this.notActivatedAccountDao = notActivatedAccountDao;
        this.myMailSender = myMailSender;
        logger.info("-------------SUCCESSFUL INIT UserServiceImpl-------------");
    }

    @Override
    @Transactional
    public UserAccountDetail signup(UserAccountDetailRequestDto userDetailDto) {

        logger.info("------------------LOGGING  signup------------------");
        UserAccountRole userAccountRole = userRoleDao.findOneUserRoleByType(UserAccountRoleType.NOT_ACTIVATED_ACCOUNT);
        UserAccount savedUser = addUser(userDetailDto.getUsername(), userDetailDto.getPassword(), userAccountRole);
        UserAccountDetail savedUserDetail = addUserDetail(savedUser, userDetailDto);
        sendMail(MailUserDetail.builder()
                .username(userDetailDto.getUsername())
                .destinationEmail(userDetailDto.getEmail())
                .firstName(userDetailDto.getFirstName())
                .lastName(userDetailDto.getLastName())
                .build());
        logger.info("-----------------SUCCESSFUL signup-----------------");
        return savedUserDetail;
    }

    @Override
    @Transactional
    public ActivatedUserDto activateAccount(String token) {

        logger.info("------------------LOGGING  activateAccount------------------");
        NotActivatedAccount notActivatedAccount = findNotActivatedAccountByToken(token);
        updateUserRoleStatusByUsername(notActivatedAccount, UserAccountRoleType.ACTIVATED_ACCOUNT);
        notActivatedAccountDao.deleteOneNotActivatedAccount(notActivatedAccount);
        logger.info("-----------------SUCCESSFUL activateAccount-----------------");
        return ActivatedUserDto.builder()
                .username(notActivatedAccount.getUsername())
                .role(UserAccountRoleType.ACTIVATED_ACCOUNT.toString())
                .build();
    }

    @Override
    public void resendRegistrationAccount(String username) {

        logger.info("------------------LOGGING  resendRegistrationAccount------------------");
        UserAccountDetail userAccountDetail = userDetailDao.findOneMyUserDetailByUsername(username);
        myMailSender.sendMail(MailUserDetail.builder()
                .username(username)
                .lastName(userAccountDetail.getLastName())
                .firstName(userAccountDetail.getFirstName())
                .destinationEmail(userAccountDetail.getEmail())
                .build());
        logger.info("-----------------SUCCESSFUL resendRegistrationAccount-----------------");
    }

    private UserAccount addUser(String username, String password, UserAccountRole userAccountRole) {

        UserAccount userAccount = userDao.saveOneUser(UserAccount.builder()
                .username(username)
                .password(password)
                .role(userAccountRole)
                .build());

        if (userAccount == null) {
            logger.error("---------------------ERROR addUser---------------------");
            throw new UserServiceException("Already exists an user with username: " + username, HttpStatus.BAD_REQUEST, UserExceptionType.DUPLICATE_USER);
        }
        return userAccount;
    }

    private UserAccountDetail addUserDetail(UserAccount savedUser, UserAccountDetailRequestDto userDetailDto) {

        UserAccountDetail savedUserDetail = userDetailDao.saveOneUserDetail(UserAccountDetail.builder()
                .user(savedUser)
                .firstName(userDetailDto.getFirstName())
                .lastName(userDetailDto.getLastName())
                .email(userDetailDto.getEmail())
                .build());

        if (savedUserDetail == null) {
            logger.error("------------------ERROR addUserDetail------------------");
            throw new UserServiceException("Already exists an user with username: " + userDetailDto.getUsername(), HttpStatus.BAD_REQUEST, UserExceptionType.DUPLICATE_USER);
        }
        return savedUserDetail;
    }

    private NotActivatedAccount findNotActivatedAccountByToken(String token) {

        NotActivatedAccount notActivatedAccount = notActivatedAccountDao.findOneNotActivatedAccountByToken(token);
        if (notActivatedAccount == null) {
            throw new UserServiceException("The token " + token + " is no longer valid!", HttpStatus.NOT_FOUND, UserExceptionType.TOKEN_NO_LONGER_VALID);
        }
        return notActivatedAccount;
    }

    private void updateUserRoleStatusByUsername(NotActivatedAccount notActivatedAccount, UserAccountRoleType type) {

        UserAccountRole userAccountRole = userRoleDao.findOneUserRoleByType(type);
        if (userDao.updateUserStatusByUsername(notActivatedAccount.getUsername(), userAccountRole) != 1) {
            this.scheduleDeleteNotActivatedAccount(notActivatedAccount);
            throw new UserServiceException("The username" + notActivatedAccount.getUsername() + " does not exists!", HttpStatus.NOT_FOUND, UserExceptionType.TOKEN_NO_LONGER_VALID);
        }
    }

    private void sendMail(MailUserDetail mailUserDetail) {

        executorService.execute(() -> {
            try {
                myMailSender.sendMail(mailUserDetail);
            } catch (UserServiceException ignored) {
            }
        });
    }

    private void scheduleDeleteNotActivatedAccount(NotActivatedAccount notActivatedAccount) {

        executorService.execute(() -> {
            notActivatedAccountDao.deleteOneNotActivatedAccount(notActivatedAccount);
        });
    }
}
