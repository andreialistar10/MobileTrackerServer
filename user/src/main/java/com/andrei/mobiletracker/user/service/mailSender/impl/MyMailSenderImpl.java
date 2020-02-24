package com.andrei.mobiletracker.user.service.mailSender.impl;

import com.andrei.mobiletracker.user.dao.notActivatedAccountDao.NotActivatedAccountDao;
import com.andrei.mobiletracker.user.model.NotActivatedAccount;
import com.andrei.mobiletracker.user.service.exception.UserExceptionType;
import com.andrei.mobiletracker.user.service.exception.UserServiceException;
import com.andrei.mobiletracker.user.service.mailSender.MailUserDetail;
import com.andrei.mobiletracker.user.service.mailSender.MyMailSender;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class MyMailSenderImpl implements MyMailSender {

    private final NotActivatedAccountDao notActivatedAccountDao;
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;
    private final Random random = new Random();
    private static final Logger logger = LogManager.getLogger(MyMailSenderImpl.class);
    private static final String prefixConfirmationUrl = "http://localhost:8080/mobile-tracker/users/confirm-account?token=";

    public MyMailSenderImpl(JavaMailSender javaMailSender, Configuration configuration, NotActivatedAccountDao notActivatedAccountDao) {

        logger.info("------------------INIT  MyMailSenderImpl------------------");
        this.notActivatedAccountDao = notActivatedAccountDao;
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
        logger.info("-------------SUCCESSFUL INIT MyMailSenderImpl-------------");
    }

    @Transactional
    @Override
    public void sendMail(MailUserDetail mailUserDetail) {

        logger.info("------------------LOGGING  sendMail------------------");
        String token = saveToken(mailUserDetail.getUsername());
        Map<String, Object> model = new HashMap<>();
        model.put("name", new StringBuilder(mailUserDetail.getFirstName()).append(" ").append(mailUserDetail.getLastName()));
        model.put("confirmation_url", prefixConfirmationUrl + token);
        sendMail(mailUserDetail.getDestinationEmail(), model);
        logger.info("-------------------FINAL sendMail-------------------");
    }

    private String saveToken(String username) {

        String token = buildToken();
        NotActivatedAccount notActivatedAccount = notActivatedAccountDao.updateOneNotActivatedAccount(NotActivatedAccount.builder()
                .token(token)
                .username(username)
                .build());
        if (notActivatedAccount == null) {
            logger.error("Token for user: {} could not be saved" + username);
            throw new UserServiceException("Token for user " + username + "could not be saved", HttpStatus.INTERNAL_SERVER_ERROR, UserExceptionType.ERROR);
        }
        return token;
    }

    private String buildToken() {

        int dim = random.nextInt(111) + 130;
        String publicToken = RandomStringUtils.random(dim, true, true);
        String uniqueToken = buildUniqueToken();
        return publicToken + uniqueToken;
    }

    private void sendMail(String destinationEmail, Map<String, Object> model) {

        logger.info("Trying to send mail to {}", destinationEmail);
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false);
            String htmlMessage = buildBodyMessage(model);

            helper.setTo(destinationEmail);
            helper.setSubject("Welcome to MobileTracker!");
            helper.setText(htmlMessage, true);

            javaMailSender.send(msg);
            logger.info("The registration mail was sent to {}", destinationEmail);
        } catch (MessagingException e) {
            logger.error("ERROR when trying to send mail: {}", e.getMessage());
            e.printStackTrace();
            throw new UserServiceException("ERROR when trying to send mail: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, UserExceptionType.ERROR);
        } catch (IOException | TemplateException e) {
            logger.error("ERROR when trying to build body message: {}", e.getMessage());
            throw new UserServiceException("ERROR when trying to build body message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, UserExceptionType.ERROR);
        }
    }

    private String buildBodyMessage(Map<String, Object> model) throws IOException, TemplateException {

        Template registerEmailTemplate = configuration.getTemplate("register-email-template.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(registerEmailTemplate, model);
    }

    private static synchronized String buildUniqueToken() {

        return "_" + System.currentTimeMillis();
    }
}
