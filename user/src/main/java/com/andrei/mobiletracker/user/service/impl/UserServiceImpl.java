package com.andrei.mobiletracker.user.service.impl;

import com.andrei.mobiletracker.user.dao.notactivatedaccount.NotActivatedAccountDao;
import com.andrei.mobiletracker.user.dao.user.UserDao;
import com.andrei.mobiletracker.user.dao.userdetail.UserDetailDao;
import com.andrei.mobiletracker.user.dto.ActivatedUserDto;
import com.andrei.mobiletracker.user.dto.UserAccountDetailRequestDto;
import com.andrei.mobiletracker.user.model.NotActivatedAccount;
import com.andrei.mobiletracker.user.model.UserAccount;
import com.andrei.mobiletracker.user.model.UserAccountDetails;
import com.andrei.mobiletracker.user.model.UserAccountRole;
import com.andrei.mobiletracker.user.service.UserService;
import com.andrei.mobiletracker.user.service.exception.UserExceptionType;
import com.andrei.mobiletracker.user.service.exception.UserServiceException;
import com.andrei.mobiletracker.user.service.mailsender.MailUserDetails;
import com.andrei.mobiletracker.user.service.mailsender.UserAccountMailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserDetailDao userDetailDao;
    private final NotActivatedAccountDao notActivatedAccountDao;
    private final UserAccountMailSender userAccountMailSender;
    private final PasswordEncoder passwordEncoder;
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserDao userDao, UserDetailDao userDetailDao, NotActivatedAccountDao notActivatedAccountDao, UserAccountMailSender userAccountMailSender, PasswordEncoder passwordEncoder) {

        logger.info("------------------INIT  UserServiceImpl------------------");
        this.userDao = userDao;
        this.userDetailDao = userDetailDao;
        this.notActivatedAccountDao = notActivatedAccountDao;
        this.userAccountMailSender = userAccountMailSender;
        this.passwordEncoder = passwordEncoder;
        logger.info("-------------SUCCESSFUL INIT UserServiceImpl-------------");
    }

    @Override
    @Transactional
    public UserAccountDetails signup(UserAccountDetailRequestDto userDetailDto) {

        logger.info("------------------LOGGING  signup------------------");
        UserAccount savedUser = addUser(userDetailDto.getUsername(), passwordEncoder.encode(userDetailDto.getPassword()));
        UserAccountDetails savedUserDetail = addUserDetail(savedUser, userDetailDto);
        sendMail(MailUserDetails.builder()
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
        updateUserRoleStatusByUsername(notActivatedAccount);
        notActivatedAccountDao.deleteOneNotActivatedAccount(notActivatedAccount);
        logger.info("-----------------SUCCESSFUL activateAccount-----------------");
        return ActivatedUserDto.builder()
                .username(notActivatedAccount.getUsername())
                .role(UserAccountRole.ACTIVATED_ACCOUNT.toString())
                .build();
    }

    @Override
    public void resendRegistrationAccount(String username) {

        logger.info("------------------LOGGING  resendRegistrationAccount------------------");
        UserAccountDetails userAccountDetails = userDetailDao.findOneMyUserDetailByUsername(username);
        userAccountMailSender.sendMail(MailUserDetails.builder()
                .username(username)
                .lastName(userAccountDetails.getLastName())
                .firstName(userAccountDetails.getFirstName())
                .destinationEmail(userAccountDetails.getEmail())
                .build());
        logger.info("-----------------SUCCESSFUL resendRegistrationAccount-----------------");
    }

    private UserAccount addUser(String username, String password) {

        UserAccount userAccount = userDao.saveOneUser(UserAccount.builder()
                .username(username)
                .password(password)
                .role(UserAccountRole.NOT_ACTIVATED_ACCOUNT)
                .build());

        if (userAccount == null) {
            logger.error("---------------------ERROR addUser---------------------");
            throw new UserServiceException("Already exists an user with username: " + username, HttpStatus.BAD_REQUEST, UserExceptionType.DUPLICATE_USER);
        }
        return userAccount;
    }

    private UserAccountDetails addUserDetail(UserAccount savedUser, UserAccountDetailRequestDto userDetailDto) {

        UserAccountDetails savedUserDetail = userDetailDao.saveOneUserDetail(UserAccountDetails.builder()
                .username(userDetailDto.getUsername())
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

    private void updateUserRoleStatusByUsername(NotActivatedAccount notActivatedAccount) {

        if (userDao.updateUserStatusByUsername(notActivatedAccount.getUsername(), UserAccountRole.ACTIVATED_ACCOUNT) != 1) {
            this.scheduleDeleteNotActivatedAccount(notActivatedAccount);
            throw new UserServiceException("The username" + notActivatedAccount.getUsername() + " does not exists!", HttpStatus.NOT_FOUND, UserExceptionType.TOKEN_NO_LONGER_VALID);
        }
    }

    private void sendMail(MailUserDetails mailUserDetail) {

        executorService.execute(() -> {
            try {
                userAccountMailSender.sendMail(mailUserDetail);
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
