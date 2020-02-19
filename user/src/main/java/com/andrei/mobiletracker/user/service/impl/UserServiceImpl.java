package com.andrei.mobiletracker.user.service.impl;

import com.andrei.mobiletracker.user.dao.userDao.UserDao;
import com.andrei.mobiletracker.user.dao.userDetailDao.UserDetailDao;
import com.andrei.mobiletracker.user.dto.MyUserDetailRequestDto;
import com.andrei.mobiletracker.user.model.MyUser;
import com.andrei.mobiletracker.user.model.MyUserDetail;
import com.andrei.mobiletracker.user.service.UserService;
import com.andrei.mobiletracker.user.service.exception.UserServiceException;
import com.andrei.mobiletracker.user.service.exception.UserExceptionType;
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
    private final MyMailSender myMailSender;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final int retryAt = 5000;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserDao userDao, UserDetailDao userDetailDao, MyMailSender myMailSender) {

        logger.info("------------------INIT  UserServiceImpl------------------");
        this.userDao = userDao;
        this.userDetailDao = userDetailDao;
        this.myMailSender = myMailSender;
        logger.info("-------------SUCCESSFUL INIT UserServiceImpl-------------");
    }

    @Override
    @Transactional
    public MyUserDetail signup(MyUserDetailRequestDto userDetailDto) {

        logger.info("------------------LOGGING  signup------------------");
        MyUser savedUser = addUser(userDetailDto.getUsername(), userDetailDto.getPassword());
        MyUserDetail savedUserDetail = addUserDetail(savedUser, userDetailDto);
        sendMail(MailUserDetail.builder()
                .destinationEmail(userDetailDto.getEmail())
                .firstName(userDetailDto.getFirstName())
                .lastName(userDetailDto.getLastName())
                .build());
        logger.info("-----------------SUCCESSFUL signup-----------------");
        return savedUserDetail;
    }

    private void sendMail(MailUserDetail mailUserDetail) {

        executorService.execute(() -> myMailSender.sendMailOrRetryAfterMillis(mailUserDetail,retryAt));
    }

    private MyUserDetail addUserDetail(MyUser savedUser, MyUserDetailRequestDto userDetailDto) {

        MyUserDetail savedUserDetail = userDetailDao.saveOneUserDetail(MyUserDetail.builder()
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

    private MyUser addUser(String username, String password) {

        MyUser myUser = userDao.saveOneUser(MyUser.builder()
                .username(username)
                .password(password)
                .build());

        if (myUser == null) {
            logger.error("---------------------ERROR addUser---------------------");
            throw new UserServiceException("Already exists an user with username: " + username, HttpStatus.BAD_REQUEST, UserExceptionType.DUPLICATE_USER);
        }
        return myUser;
    }
}
