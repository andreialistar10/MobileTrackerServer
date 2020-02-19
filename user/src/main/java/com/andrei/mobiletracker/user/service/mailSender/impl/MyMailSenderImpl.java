package com.andrei.mobiletracker.user.service.mailSender.impl;

import com.andrei.mobiletracker.user.service.mailSender.MailUserDetail;
import com.andrei.mobiletracker.user.service.mailSender.MyMailSender;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyMailSenderImpl implements MyMailSender {

    private static final Logger logger = LogManager.getLogger(MyMailSenderImpl.class);
    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    public MyMailSenderImpl(JavaMailSender javaMailSender, Configuration configuration) {

        logger.info("------------------INIT  MyMailSenderImpl------------------");
        this.javaMailSender = javaMailSender;
        logger.info("-------------SUCCESSFUL INIT MyMailSenderImpl-------------");
        this.configuration = configuration;
    }

    @Override
    public void sendMailOrRetryAfterMillis(MailUserDetail mailUserDetail, int retryAfter) {

        logger.info("------------------LOGGING  sendMailOrRetryAfterMillis------------------");
//        while (true) {
        for (int i = 0; i < 2; ++i) {
            try {
                Map<String, Object> model = new HashMap<>();
                model.put("name", new StringBuilder(mailUserDetail.getFirstName()).append(" ").append(mailUserDetail.getLastName()));
                sendMail(mailUserDetail.getDestinationEmail(), model);
                break;
            } catch (Exception ex) {
                if (handleSendingMailError(mailUserDetail, retryAfter, ex)) break;
            }
        }
        logger.info("-------------------FINAL sendMailOrRetryAfterMillis-------------------");
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

//        String message = String.format("<html><body><p>Hello dear <b>%s</b></p><br><br><p>We are so glad you registered in our application!</p><br>\n" +
//                "<p>The next step is to confirm your account by clicking <a href='www.google.com'>here</a>.</p><br><p>Our best,</p><p><b>The MobileTracker team!</b></p></body></html>", completeName);
        Template registerEmailTemplate = configuration.getTemplate("register-email-template.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(registerEmailTemplate, model);
    }

    private boolean handleSendingMailError(MailUserDetail mailUserDetail, int retryAfter, Exception ex) {

        logger.error("Error when trying to send mail to: {} message:{}", mailUserDetail.getDestinationEmail(), ex.getMessage());
        ex.printStackTrace();
        boolean sleepy = sleepCurrentThread(retryAfter, mailUserDetail.getDestinationEmail());
        return !sleepy;
    }

    private boolean sleepCurrentThread(int retryAfter, String destinationEmail) {

        boolean sleepy = false;
        try {
            Thread.sleep(retryAfter);
            sleepy = true;
        } catch (InterruptedException exception) {
            logger.error("Error when trying to sleep thread for send mail to :{}", destinationEmail);
            exception.printStackTrace();
        }
        return sleepy;
    }
}
