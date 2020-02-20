package com.andrei.mobiletracker.user.service.mailSender.impl;

import com.andrei.mobiletracker.user.dao.notActivatedAccountDao.NotActivatedAccountDao;
import com.andrei.mobiletracker.user.model.NotActivatedAccount;
import com.andrei.mobiletracker.user.service.mailSender.MailUserDetail;
import com.andrei.mobiletracker.user.service.mailSender.MyMailSender;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

        logger.info("------------------LOGGING  sendMailOrRetryAfterMillis------------------");
        try {
            String token = saveToken(mailUserDetail.getUsername());
            Map<String, Object> model = new HashMap<>();
            model.put("name", new StringBuilder(mailUserDetail.getFirstName()).append(" ").append(mailUserDetail.getLastName()));
            model.put("confirmation_url", prefixConfirmationUrl+token);
            sendMail(mailUserDetail.getDestinationEmail(), model);
        } catch (Exception ex) {
            logger.error("Error when trying to send mail to: {} message:{}", mailUserDetail.getDestinationEmail(), ex.getMessage());
            ex.printStackTrace();
        }
        logger.info("-------------------FINAL sendMailOrRetryAfterMillis-------------------");
    }

    private String saveToken(String username) {

        NotActivatedAccount notActivatedAccount;
        do {
            String token = buildToken();
            notActivatedAccount = notActivatedAccountDao.saveOneNotActivatedAccount(NotActivatedAccount.builder()
                    .token(token)
                    .username(username)
                    .build());
        } while (notActivatedAccount == null);

        return notActivatedAccount.getToken();
    }

    private String buildToken() {

        int dim = random.nextInt(128) + 129;
        return RandomStringUtils.random(dim, true, true);
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
        } catch (IOException | TemplateException e) {
            logger.error("ERROR when trying to build body message: {}", e.getMessage());
        }
    }

    private String buildBodyMessage(Map<String, Object> model) throws IOException, TemplateException {

        Template registerEmailTemplate = configuration.getTemplate("register-email-template.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(registerEmailTemplate, model);
    }
}
