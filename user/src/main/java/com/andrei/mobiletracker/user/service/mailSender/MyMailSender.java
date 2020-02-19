package com.andrei.mobiletracker.user.service.mailSender;

public interface MyMailSender {

    void sendMailOrRetryAfterMillis(MailUserDetail mailUserDetail,int retryAfter);
}
