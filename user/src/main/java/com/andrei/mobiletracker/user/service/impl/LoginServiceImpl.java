package com.andrei.mobiletracker.user.service.impl;

import com.andrei.mobiletracker.user.dao.userDao.UserDao;
import com.andrei.mobiletracker.user.dto.LoggedInUserDto;
import com.andrei.mobiletracker.user.dto.UserDto;
import com.andrei.mobiletracker.user.model.MyUser;
import com.andrei.mobiletracker.user.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoginServiceImpl implements LoginService {

    private final UserDao userDao;
    private static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

    public LoginServiceImpl(UserDao userDao) {

        logger.info("------------------INIT  LoginServiceImpl------------------");
        this.userDao = userDao;
        logger.info("-------------SUCCESSFUL INIT LoginServiceImpl-------------");
    }

    @Override
    public LoggedInUserDto login(UserDto user) {

        logger.info("------------------LOGGING  login------------------");
        logger.info("-----------------SUCCESSFUL login-----------------");
        return null;
    }
}
